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

/**
 * Exception indicating that a given {@link com.axisframework.eventhandling.EventSequencingPolicy} could not be
 * initialized.
 *
 * @author Allard Buijze
 * @since 0.3
 */
public class UnsupportedPolicyException extends RuntimeException {

    private static final long serialVersionUID = -320609424758607152L;

    /**
     * Initializes this exception with given <code>message</code> and <code>cause</code>.
     *
     * @param message The message describing the cause
     * @param cause   The original cause of this exception
     */
    public UnsupportedPolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}
