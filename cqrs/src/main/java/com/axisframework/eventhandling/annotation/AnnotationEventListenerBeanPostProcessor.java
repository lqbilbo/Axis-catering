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

package com.axisframework.eventhandling.annotation;

import com.axisframework.eventhandling.EventBus;
import com.axisframework.eventhandling.EventListener;
import com.axisframework.util.AbstractAnnotationHandlerBeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Spring Bean post processor that automatically generates an adapter for each bean containing {@link EventHandler}
 * annotated methods.
 *
 * @author Allard Buijze
 * @since 0.3
 */
public class AnnotationEventListenerBeanPostProcessor extends AbstractAnnotationHandlerBeanPostProcessor {

    private Executor executor;
    private EventBus eventBus;

    @Override
    protected Class<?> getAdapterInterface() {
        return EventListener.class;
    }

    /**
     * Create an AnnotationEventListenerAdapter instance of the given {@code bean}. This adapter will receive all event
     * handler calls to be handled by this bean.
     *
     * @param bean The bean that the EventListenerAdapter has to adapt
     * @return an event handler adapter for the given {@code bean}
     */
    protected AnnotationEventListenerAdapter initializeAdapterFor(Object bean) {
        AnnotationEventListenerAdapter adapter = new AnnotationEventListenerAdapter(bean, executor, eventBus);
        adapter.subscribe();
        return adapter;
    }

    @Override
    protected boolean isPostProcessingCandidate(Class<?> targetClass) {
        return isNotEventHandlerSubclass(targetClass) && hasEventHandlerMethod(targetClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public void afterPropertiesSet() throws Exception {
        // if no EventBus is set, find one in the application context
        if (eventBus == null) {
            Map<String, EventBus> beans = getApplicationContext().getBeansOfType(EventBus.class);
            if (beans.size() != 1) {
                throw new IllegalStateException("If no specific EventBus is provided, the application context must "
                        + "contain exactly one bean of type EventBus. The current application context has: "
                        + beans.size());
            }
            this.eventBus = beans.entrySet().iterator().next().getValue();
        }
    }

    private boolean isNotEventHandlerSubclass(Class<?> beanClass) {
        return !EventListener.class.isAssignableFrom(beanClass);
    }

    private boolean hasEventHandlerMethod(Class<?> beanClass) {
        final AtomicBoolean result = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(beanClass, new HasEventHandlerAnnotationMethodCallback(result));
        return result.get();
    }

    /**
     * Sets the Executor to use when the AnnotationEventListenerBeanPostProcessor encounters event listeners w
     *
     * @param executor the Executor to use for asynchronous event listeners
     */
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    /**
     * Sets the event bus to which detected event listeners should be subscribed. If none is provided, the event bus
     * will be automatically detected in the application context.
     *
     * @param eventBus the event bus to subscribe detected event listeners to
     */
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return false;
    }

    private static final class HasEventHandlerAnnotationMethodCallback implements ReflectionUtils.MethodCallback {

        private final AtomicBoolean result;

        private HasEventHandlerAnnotationMethodCallback(AtomicBoolean result) {
            this.result = result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
            if (method.isAnnotationPresent(EventHandler.class)) {
                result.set(true);
            }
        }
    }
}
