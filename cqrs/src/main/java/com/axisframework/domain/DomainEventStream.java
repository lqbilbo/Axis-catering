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

/**
 * The DomainEventStream represents a stream of historical domain events. The order of events in this stream must
 * represent the actual chronological order in which the events happened. A DomainEventStream may provide access to all
 * events (from the first to the most recent) or any subset of these.
 *
 * @author Allard Buijze
 * @since 0.1
 */
public interface DomainEventStream {

    /**
     * Returns <code>true</code> if the stream has more events, meaning that a call to <code>next()</code> will not
     * result in an exception. If a call to this method returns <code>false</code>, there is no guarantee about the
     * result of a consecutive call to <code>next()</code>
     *
     * @return <code>true</code> if the stream contains more events.
     */
    boolean hasNext();

    /**
     * Returns the next events in the stream, if available. Use <code>hasNext()</code> to obtain a guarantee about the
     * availability of any next event. Each call to <code>next()</code> will forward the pointer to the next event in
     * the stream.
     * <p/>
     * If the pointer has reached the end of the stream, the behavior of this method is undefined. It could either
     * return <code>null</code>, or throw an exception, depending on the actual implementation. Use {@link #hasNext()}
     * to confirm the existence of elements after the current pointer.
     *
     * @return the next event in the stream.
     */
    DomainEvent next();

    /**
     * Returns the next events in the stream, if available, without moving the pointer forward. Hence, a call to {@link
     * #next()} will return the same event as a call to <code>peek()</code>. Use <code>hasNext()</code> to obtain a
     * guarantee about the availability of any next event.
     * <p/>
     * If the pointer has reached the end of the stream, the behavior of this method is undefined. It could either
     * return <code>null</code>, or throw an exception, depending on the actual implementation. Use {@link #hasNext()}
     * to confirm the existence of elements after the current pointer.
     *
     * @return the next event in the stream.
     */
    DomainEvent peek();
}
