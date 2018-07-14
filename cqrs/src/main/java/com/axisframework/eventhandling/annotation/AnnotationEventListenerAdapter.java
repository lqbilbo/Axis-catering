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

import com.axisframework.domain.Event;
import com.axisframework.eventhandling.AsynchronousEventHandlerWrapper;
import com.axisframework.eventhandling.EventBus;
import com.axisframework.eventhandling.EventListener;
import com.axisframework.eventhandling.EventSequencingPolicy;
import com.axisframework.eventhandling.SequentialPolicy;
import com.axisframework.eventhandling.TransactionManager;
import com.axisframework.eventhandling.TransactionStatus;
import com.axisframework.util.AnnotatedHandlerAdapter;
import com.axisframework.util.FieldAccessibilityCallback;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static java.security.AccessController.doPrivileged;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * Adapter that turns any bean with {@link EventHandler} annotated methods into an {@link
 * EventListener}.
 * <p/>
 * If the event listener has the {@link @AsynchronousEventListener} annotation, it is also configured to handle events
 * asynchronously.
 * <p/>
 *
 * @author Allard Buijze
 * @see EventListener
 * @see AsynchronousEventHandlerWrapper
 * @since 0.1
 */
public class AnnotationEventListenerAdapter implements AnnotatedHandlerAdapter, EventListener, TransactionManager {

    private final EventListener targetEventListener;
    private final Executor executor;
    private final TransactionManager transactionManager;
    private final EventBus eventBus;

    /**
     * Initialize the AnnotationEventListenerAdapter for the given <code>annotatedEventListener</code>. When the adapter
     * subscribes, it will subscribe to the given event bus.
     *
     * @param annotatedEventListener the event listener
     * @param eventBus               the event bus to register the event listener to
     */
    public AnnotationEventListenerAdapter(Object annotatedEventListener, EventBus eventBus) {
        this(annotatedEventListener, null, eventBus);
    }

    /**
     * Initialize the AnnotationEventListenerAdapter for the given <code>annotatedEventListener</code>. If the
     * <code>annotatedEventListener</code> is asynchronous (has the {@link AsynchronousEventListener}) annotation) then
     * the given executor is used to execute event processing.
     *
     * @param annotatedEventListener the event listener
     * @param executor               The executor to use when wiring an Asynchronous Event Listener.
     * @param eventBus               the event bus to register the event listener to
     */
    public AnnotationEventListenerAdapter(Object annotatedEventListener, Executor executor, EventBus eventBus) {
        EventListener adapter = new TargetEventListener(new AnnotationEventHandlerInvoker(annotatedEventListener));
        this.transactionManager = createTransactionManagerFor(annotatedEventListener);
        this.executor = executor;
        this.eventBus = eventBus;

        if (findAnnotation(annotatedEventListener.getClass(), AsynchronousEventListener.class) != null) {
            if (executor == null) {
                throw new IllegalArgumentException(
                        "The annotatedEventListener is Asynchronous, but no executor is provided.");
            }
            adapter = createAsynchronousWrapperForBean(annotatedEventListener, adapter);
        }
        this.targetEventListener = adapter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Event event) {
        targetEventListener.handle(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeTransaction(TransactionStatus transactionStatus) {
        transactionManager.beforeTransaction(transactionStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterTransaction(TransactionStatus transactionStatus) {
        transactionManager.afterTransaction(transactionStatus);
    }

    /**
     * Unsubscribe the EventListener with the configured EventBus.
     */
    @PreDestroy
    public void unsubscribe() {
        eventBus.unsubscribe(this);
    }

    /**
     * Subscribe the EventListener with the configured EventBus.
     */
    @PostConstruct
    public void subscribe() {
        eventBus.subscribe(this);
    }

    private TransactionManager createTransactionManagerFor(Object bean) {
        TransactionManager tm;
        if (bean instanceof TransactionManager) {
            tm = (TransactionManager) bean;
        } else if (hasTransactionalMethods(bean)) {
            tm = new AnnotationTransactionManager(bean);
        } else {
            tm = findTransactionManagerInField(bean);
        }
        if (tm == null) {
            tm = new AnnotationTransactionManager(bean);
        }
        return tm;
    }

    private TransactionManager findTransactionManagerInField(Object bean) {
        TransactionManagerFieldCallback transactionManagerFieldCallback = new TransactionManagerFieldCallback(bean);
        ReflectionUtils.doWithFields(bean.getClass(), transactionManagerFieldCallback);
        return transactionManagerFieldCallback.getTransactionManager();
    }

    private boolean hasTransactionalMethods(Object bean) {
        HasTransactionMethodCallback hasTransactionMethodCallback = new HasTransactionMethodCallback();
        ReflectionUtils.doWithMethods(bean.getClass(), hasTransactionMethodCallback);
        return hasTransactionMethodCallback.isFound();

    }

    private AsynchronousEventHandlerWrapper createAsynchronousWrapperForBean(Object bean,
                                                                             EventListener adapter) {

        return new AsynchronousEventHandlerWrapper(adapter,
                                                   transactionManager,
                                                   getSequencingPolicyFor(bean),
                                                   executor);
    }

    private EventSequencingPolicy getSequencingPolicyFor(Object annotatedEventListener) {
        AsynchronousEventListener annotation = findAnnotation(annotatedEventListener.getClass(),
                                                              AsynchronousEventListener.class);
        if (annotation == null) {
            return new SequentialPolicy();
        }

        Class<? extends EventSequencingPolicy> policyClass = annotation.sequencingPolicyClass();
        try {
            return policyClass.newInstance();
        } catch (InstantiationException e) {
            throw new UnsupportedPolicyException(String.format(
                    "Could not initialize an instance of the given policy: [%s]. "
                            + "Does it have an accessible no-arg constructor?",
                    policyClass.getSimpleName()), e);
        } catch (IllegalAccessException e) {
            throw new UnsupportedPolicyException(String.format(
                    "Could not initialize an instance of the given policy: [%s]. "
                            + "Is the no-arg constructor accessible?",
                    policyClass.getSimpleName()), e);
        }
    }

    private static final class TargetEventListener implements EventListener {

        private final AnnotationEventHandlerInvoker eventHandlerInvoker;

        public TargetEventListener(AnnotationEventHandlerInvoker eventHandlerInvoker) {
            this.eventHandlerInvoker = eventHandlerInvoker;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void handle(Event event) {
            eventHandlerInvoker.invokeEventHandlerMethod(event);
        }
    }

    private static class HasTransactionMethodCallback implements ReflectionUtils.MethodCallback {

        private final AtomicBoolean found = new AtomicBoolean(false);

        @Override
        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
            if (method.isAnnotationPresent(BeforeTransaction.class)
                    || method.isAnnotationPresent(AfterTransaction.class)) {
                found.set(true);
            }
        }

        public boolean isFound() {
            return found.get();
        }

    }

    private static class TransactionManagerFieldCallback implements ReflectionUtils.FieldCallback {

        private final AtomicReference<TransactionManager> tm;
        private final Object bean;

        public TransactionManagerFieldCallback(Object bean) {
            this.bean = bean;
            tm = new AtomicReference<TransactionManager>();
        }

        @Override
        public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
            if (field.isAnnotationPresent(com.axisframework.eventhandling.annotation.TransactionManager.class)) {
                doPrivileged(new FieldAccessibilityCallback(field));

                if (TransactionManager.class.isAssignableFrom(field.getType())) {
                    tm.set((TransactionManager) field.get(bean));
                } else {
                    tm.set(new AnnotationTransactionManager(field.get(bean)));
                }
            }
        }

        public TransactionManager getTransactionManager() {
            return tm.get();
        }
    }
}
