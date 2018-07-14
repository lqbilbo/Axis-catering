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

package com.axisframework.eventsourcing;

import com.axisframework.domain.AbstractAggregateRoot;
import com.axisframework.domain.AggregateDeletedEvent;
import com.axisframework.domain.DomainEvent;
import com.axisframework.domain.DomainEventStream;
import com.axisframework.util.Assert;

import java.util.UUID;

/**
 * Abstract convenience class to be extended by all aggregate roots. The AbstractEventSourcedAggregateRoot tracks all
 * uncommitted events. It also provides convenience methods to initialize the state of the aggregate root based on a
 * {@link DomainEventStream}, which can be used for event sourcing.
 *
 * @author Allard Buijze
 * @since 0.1
 */
public abstract class AbstractEventSourcedAggregateRoot extends AbstractAggregateRoot
        implements EventSourcedAggregateRoot {

    /**
     * Initializes the aggregate root using a random aggregate identifier.
     */
    protected AbstractEventSourcedAggregateRoot() {
        super();
    }

    /**
     * Initializes the aggregate root using the provided aggregate identifier.
     *
     * @param identifier the identifier of this aggregate
     */
    protected AbstractEventSourcedAggregateRoot(UUID identifier) {
        super(identifier);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException     if this aggregate was already initialized.
     * @throws AggregateDeletedException if the event stream contains an event of type {@link AggregateDeletedEvent} (or
     *                                   subtype).
     */
    @Override
    public void initializeState(DomainEventStream domainEventStream) {
        Assert.state(getUncommittedEventCount() == 0, "Aggregate is already initialized");
        long lastSequenceNumber = -1;
        while (domainEventStream.hasNext()) {
            DomainEvent event = domainEventStream.next();
            if (event instanceof AggregateDeletedEvent) {
                throw new AggregateDeletedException(String.format(
                        "Aggregate with identifier [%s] not found. It has been deleted.",
                        event.getAggregateIdentifier()));
            }
            lastSequenceNumber = event.getSequenceNumber();
            handle(event);
        }
        initializeEventStream(lastSequenceNumber);
    }

    /**
     * Apply the provided event. Applying events means they are added to the uncommitted event queue and forwarded to
     * the {@link #handle(DomainEvent)} event handler method} for processing.
     *
     * @param event The event to apply
     */
    protected void apply(DomainEvent event) {
        registerEvent(event);
        handle(event);
    }

    /**
     * Apply state changes based on the given event.
     * <p/>
     * Note: Implementations of this method should *not* perform validation.
     *
     * @param event The event to handle
     */
    protected abstract void handle(DomainEvent event);

}
