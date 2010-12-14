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
package org.code_house.webconsole.core.api.plugin;

import org.code_house.webconsole.core.api.Extension;

/**
 * Plugin is abstract element wchich allows to provide general information
 * for multiple extensions.
 * 
 * Extension definition doesn't require plugin definition. Plugin are just
 * informations about bundle with extensions. Relation between plugin and bundle
 * is 1:1. Only one plugin can be defined in each bundle.
 * 
 * @author luke@code-house.org
 */
public interface Plugin extends Extension {

    /**
     * Gets vendor name.
     * 
     * @return Name of the vendor.
     */
    String getVendor();

    /**
     * Gets human readable plugin name.
     * 
     * @return Plugin name.
     */
    String getName();

    /**
     * Gets plugin url.
     * 
     * @return Plugin url.
     */
    String getUrl();

}
