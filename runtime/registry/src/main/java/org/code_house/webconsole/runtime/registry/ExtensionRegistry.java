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

import java.util.List;

import org.code_house.webconsole.core.api.ExtensionPoint;

/**
 * Extension registry to track extensions points and extensions.
 * 
 * Interface contains only read only methods. Extension point tracking should be
 * done in registry implementation to avoid registering extensions by hand.
 * 
 * All get* methods from this class may throw {@link MissingExtensionPointException}.
 * To avoid exceptions use method isRegistered before you try get extensions.
 * 
 * @author luke@code-house.org
 */
public interface ExtensionRegistry {

    /**
     * Get all registered extension points.
     * 
     * @return List of extension points registered in registry.
     */
    List<ExtensionPoint> getExtensionPoints();

    /**
     * Get extension point by type.
     * 
     * @param type Extension type.
     * @return Extension point for given type.
     */
    ExtensionPoint getExtensionPoint(String type);

    /**
     * Check if given extension point is registered.
     * 
     * @param type Extension type.
     * @return True if extension is registered otherwise false.
     */
    boolean isRegistered(String type);

}
