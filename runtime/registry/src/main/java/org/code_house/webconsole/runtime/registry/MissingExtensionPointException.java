/*
 * Copyright 2010 Code-House
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.code_house.webconsole.runtime.registry;

/**
 * Exception thrown when someone try to get extension point which is missing
 * in registry.
 * 
 * @author luke@code-house.org
 */
@SuppressWarnings("serial")
public class MissingExtensionPointException extends RuntimeException {

    /**
     * Creates new exception.
     * 
     * @param message Exception message.
     */
    public MissingExtensionPointException(String message) {
        super(message);
    }


}
