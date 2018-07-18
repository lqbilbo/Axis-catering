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

import com.axisframework.domain.DomainEventStream;
import com.axisframework.domain.VersionedAggregateRoot;

/**
 * Aggregate that can be initialized using a {@link DomainEventStream}. Aggregates that are
 * initialized using Event Sourcing should implement this interface.
 *
 * @author Allard Buijze
 * @see com.axisframework.eventsourcing.EventSourcingRepository
 * @since 0.3
 */
public interface EventSourcedAggregateRoot extends VersionedAggregateRoot {

    /**
     * Initialize the state of this aggregate using the events in the provided {@link
     * DomainEventStream}. A call to this method on an aggregate that has already been
     * initialized will result in an {@link IllegalStateException}.
     *
     * @param domainEventStream the event stream containing the events that describe the state changes of this
     *                          aggregate
     * @throws IllegalStateException if this aggregate was already initialized.
     */
    void initializeState(DomainEventStream domainEventStream);
}
