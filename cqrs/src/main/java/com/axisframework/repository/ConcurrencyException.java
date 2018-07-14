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

package com.axisframework.repository;

/**
 * Exception indicating that concurrent access to a repository was detected. Most likely, two threads were modifying the
 * same aggregate.
 *
 * @author Allard Buijze
 * @since 0.3
 */
public class ConcurrencyException extends RuntimeException {

    private static final long serialVersionUID = -6645466649238331044L;

    /**
     * Initialize a ConcurrencyException with the given <code>message</code>
     *
     * @param message The message describing the cause of the exception
     */
    public ConcurrencyException(String message) {
        super(message);
    }
}
