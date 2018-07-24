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

package com.axisframework.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base interface for all events in the application. All classes that represent an event should implement this
 * interface.
 * <p/>
 * Consider implementing one of the abstract subclasses to categorize events into one of three categories: <ul>
 * <li>{@link com.axisframework.domain.DomainEvent}: Events that represent a state change of an aggregate. <li>{@link
 * com.axisframework.domain.ApplicationEvent}: Events that do not represent a state change of an aggregate, but do have
 * an important meaning to the application. <li>{@link SystemEvent}: Events that represent a state change or
 * notification of a subsystem. These events typically notify the application that part of it is not available. </ul>
 *
 * @author Allard Buijze
 * @see com.axisframework.domain.DomainEvent
 * @see com.axisframework.domain.ApplicationEvent
 * @see SystemEvent
 * @since 0.4
 */
public interface Event extends Serializable {

    /**
     * Returns the identifier of this event.
     *
     * @return the identifier of this event.
     */
    UUID getEventIdentifier();

    /**
     * Returns the timestamp of this event. The timestamp is set to the date and time the event was created.
     *
     * @return the timestamp of this event.
     */
    LocalDateTime getTimestamp();
}
