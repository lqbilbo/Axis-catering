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

package com.axisframework.eventsourcing.annotation;

import com.axisframework.domain.AggregateDeletedEvent;
import com.axisframework.domain.DomainEvent;
import com.axisframework.eventhandling.annotation.AnnotationEventHandlerInvoker;
import com.axisframework.eventhandling.annotation.EventHandler;
import com.axisframework.eventsourcing.AbstractEventSourcedAggregateRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Convenience super type for aggregate roots that have their event handler methods annotated with the {@link
 * EventHandler} annotation.
 * <p/>
 * Implementations can call the {@link #apply(DomainEvent)} method to have an event applied.
 * <p/>
 * Any events that are passed to the {@link #apply(DomainEvent)} method for which no event
 * handler can be found will cause an {@link com.axisframework.eventsourcing.annotation.UnhandledEventException} to be thrown.
 *
 * @author Allard Buijze
 * @see EventHandler
 * @since 0.1
 */
public abstract class AbstractAnnotatedAggregateRoot extends AbstractEventSourcedAggregateRoot {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAnnotatedAggregateRoot.class);
    private final AnnotationEventHandlerInvoker eventHandlerInvoker;

    /**
     * Initialize the aggregate root with a random identifier
     */
    protected AbstractAnnotatedAggregateRoot() {
        super();
        eventHandlerInvoker = new AnnotationEventHandlerInvoker(this);
    }

    /**
     * Initializes the aggregate root using the provided aggregate identifier.
     *
     * @param identifier the identifier of this aggregate
     */
    protected AbstractAnnotatedAggregateRoot(UUID identifier) {
        super(identifier);
        eventHandlerInvoker = new AnnotationEventHandlerInvoker(this);
    }

    /**
     * Calls the appropriate {@link EventHandler} annotated handler with the
     * provided event.
     *
     * @param event The event to handle
     * @see EventHandler
     */
    @Override
    protected void handle(DomainEvent event) {
        eventHandlerInvoker.invokeEventHandlerMethod(event);
    }

    /**
     * Event Handler that will throw an exception if no better (read any) event handler could be found for the processed
     * event. Throws an {@link com.axisframework.eventsourcing.annotation.UnhandledEventException}.
     *
     * @param event the event that could not be processed by any other event handler
     * @throws com.axisframework.eventsourcing.annotation.UnhandledEventException when called.
     */
    @EventHandler
    protected void onUnhandledEvents(DomainEvent event) {
        String message = String.format("No EventHandler method could be found for [%s] on aggregate [%s]",
                                       event.getClass().getSimpleName(),
                                       getClass().getSimpleName());
        logger.error(message);
        throw new UnhandledEventException(message, event);
    }

    /**
     * Event Handler that suppresses errors when an AggregateDeletedEvent is applied, but is not handled by this
     * aggregate. Typically, these events do not change any state within the aggregate.
     *
     * @param event The deletion event
     */
    @EventHandler
    protected void onUnhandledDeleteEvent(AggregateDeletedEvent event) {
        // that is allowed.
    }

}
