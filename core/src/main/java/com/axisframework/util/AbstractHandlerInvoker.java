/*
 * Copyright (c) 2010. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.axisframework.util;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.security.AccessController.doPrivileged;

/**
 * @author Allard Buijze
 */
public abstract class AbstractHandlerInvoker {

    private final Object target;
    private final Class<? extends Annotation> annotationType;

    /**
     * Initialize a handler invoker for the given <code>target</code> object that has handler method annotated with
     * given <code>annotationType</code>.
     *
     * @param target         The target to invoke methods on
     * @param annotationType The type of annotation used to demarcate the handler methods
     */
    public AbstractHandlerInvoker(Object target, Class<? extends Annotation> annotationType) {
        this.target = target;
        this.annotationType = annotationType;
    }

    /**
     * Invoke the handler demarcated with the given <code>annotationClass</code> on the target for the given
     * <code>event</code>.
     *
     * @param parameter the event to handle
     * @return the return value of the invocation
     *
     * @throws IllegalAccessException    When a security policy prevents invocation using reflection
     * @throws InvocationTargetException If the handler method threw an exception
     */
    protected Object invokeHandlerMethod(Object parameter) throws InvocationTargetException, IllegalAccessException {
        return invokeHandlerMethod(parameter, null);
    }

    /**
     * Invoke the handler demarcated with the given <code>annotationClass</code> on the target for the given
     * <code>event</code> and an optional <code>secondHandlerParameter</code>.
     *
     * @param parameter              the event to handle
     * @param secondHandlerParameter An optional second parameter allowed on the annotated method
     * @return the return value of the invocation
     *
     * @throws IllegalAccessException    When a security policy prevents invocation using reflection
     * @throws InvocationTargetException If the handler method threw an exception
     */
    protected Object invokeHandlerMethod(Object parameter, Object secondHandlerParameter)
            throws InvocationTargetException, IllegalAccessException {
        final Method m = findHandlerMethod(parameter.getClass());
        if (m == null) {
            // event listener doesn't support this type of event
            return onNoMethodFound(parameter.getClass());
        }
        if (!m.isAccessible()) {
            doPrivileged(new MethodAccessibilityCallback(m));
        }
        if (m.getParameterTypes().length == 1) {
            Object retVal = m.invoke(target, parameter);
            // let's make a clear distinction between null return value and void methods
            if (Void.TYPE.equals(m.getReturnType())) {
                return Void.TYPE;
            }
            return retVal;
        } else {
            return m.invoke(target, parameter, secondHandlerParameter);
        }
    }

    /**
     * Indicates what needs to happen when no handler is found for a given parameter. The default behavior is to return
     * {@link Void#TYPE}.
     *
     * @param parameterType The type of parameter for which no handler could be found
     * @return the value to return when no handler method is found. Defaults to {@link Void#TYPE}.
     */
    protected Object onNoMethodFound(Class<?> parameterType) {
        return Void.TYPE;
    }

    /**
     * Returns the handler method that handles objects of the given <code>parameterType</code>. Returns
     * <code>null</code> is no such method is found.
     *
     * @param parameterType The parameter type to find a handler for
     * @return the  handler method for the given parameterType
     */
    protected Method findHandlerMethod(final Class<?> parameterType) {
        MostSuitableHandlerCallback callback = new MostSuitableHandlerCallback(parameterType, annotationType);
        ReflectionUtils.doWithMethods(target.getClass(), callback, callback);
        return callback.foundHandler();
    }

    /**
     * Returns the target on which handler methods are invoked
     *
     * @return the target on which handler methods are invoked
     */
    public Object getTarget() {
        return target;
    }

    /**
     * MethodCallback and MethodFilter implementation that finds the most suitable event handler method for an event of
     * given type.
     * <p/>
     * Note that this callback must used both as MethodCallback and MethodCallback.
     * <p/>
     * Example:<br/> <code>MostSuitableHandlerCallback callback = new MostSuitableHandlerCallback(eventType) <br/>
     * ReflectionUtils.doWithMethods(eventListenerClass, callback, callback);</code>
     */
    private static class MostSuitableHandlerCallback
            implements ReflectionUtils.MethodCallback, ReflectionUtils.MethodFilter {

        private final Class<?> parameterType;
        private final Class<? extends Annotation> annotationClass;
        private Method bestMethodSoFar;

        /**
         * Initialize this callback for the given event class. The callback will find the most suitable method for an
         * event of given type.
         *
         * @param parameterType   The type of event to find the handler for
         * @param annotationClass The annotation demarcating handler methods
         */
        public MostSuitableHandlerCallback(Class<?> parameterType, Class<? extends Annotation> annotationClass) {
            this.parameterType = parameterType;
            this.annotationClass = annotationClass;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean matches(Method method) {
            Method foundSoFar = bestMethodSoFar;
            Class<?> classUnderInvestigation = method.getDeclaringClass();
            boolean bestInClassFound =
                    foundSoFar != null
                            && !classUnderInvestigation.equals(foundSoFar.getDeclaringClass())
                            && classUnderInvestigation.isAssignableFrom(foundSoFar.getDeclaringClass());
            return !bestInClassFound && method.isAnnotationPresent(annotationClass)
                    && method.getParameterTypes()[0].isAssignableFrom(parameterType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
            // method is eligible, but is it the best?
            if (bestMethodSoFar == null) {
                // if we have none yet, this one is the best
                bestMethodSoFar = method;
            } else if (bestMethodSoFar.getDeclaringClass().equals(method.getDeclaringClass())
                    && bestMethodSoFar.getParameterTypes()[0].isAssignableFrom(
                    method.getParameterTypes()[0])) {
                // this one is more specific, so it wins
                bestMethodSoFar = method;
            }
        }

        /**
         * Returns the event handler suitable for the given event, or null if no suitable event handler could be found.
         *
         * @return the found event handler, or null if none could be found
         */
        public Method foundHandler() {
            return bestMethodSoFar;
        }
    }
}
