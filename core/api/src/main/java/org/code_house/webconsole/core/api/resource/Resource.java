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
package org.code_house.webconsole.core.api.resource;

import java.net.URL;

import org.code_house.webconsole.core.api.Extension;

/**
 * Extension to define resource provision. Each resource have two identifiers:
 * <ul>
 *  <li><strong>Alias</strong> is abstract location which should be used 
 *  to request resource.</li>
 *  <li><strong>Location</strong> is psychical place where resource is stored 
 *  and should be read from</li>
 * </ul>
 * 
 * @author luke@code-house.org
 */
public interface Resource extends Extension {

    /**
     * Resource alias.
     * 
     * @return Resource alias.
     */
    String getAlias();

    /**
     * Returns location of the resource.
     * 
     * @return URL which points to resource location.
     */
    URL getLocation();

}
