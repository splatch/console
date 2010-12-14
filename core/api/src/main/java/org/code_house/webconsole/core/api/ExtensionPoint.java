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
package org.code_house.webconsole.core.api;

import java.util.List;
import java.util.Set;

/**
 * Extension point definition. In WebConsole point of extension is always
 * releated with interface (T). This interface should contain all methods needed
 * to Extension usage.
 * 
 * Extension developers doesn't have to implement this interface nor provide any
 * instances of Extension Point.
 * 
 * The relation betwen extension and extension point is one to one, each extension
 * can be releated only with one extension point. One extension point can contain
 * only one type of extension.
 * 
 * @author luke@code-house.org
 */
public interface ExtensionPoint {

    /**
     * Returns extension point type (value of T type alias).
     * 
     * @return Extension point type.
     */
    String getType();

    /**
     * Get extensions for given extension point.
     * 
     * @return Extensions.
     */
    Set<Extension> getExtensions();

    /**
     * Get extensions for given point of given type.
     * 
     * This method may throw exception if type is unknown for extension point,
     * eg. when you try to get extensions not associated with extension point.
     * 
     * @param type Type of extension.
     * @return Extension of given types.
     */
    Set<Extension> getExtensions(Class type);

    /**
     * Get types of extensions assigned with extension point.
     * 
     * @return Extension types.
     */
    List<Class> getExtensionTypes();

}
