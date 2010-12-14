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
package org.code_house.webconsole.core.common.plugin;

import org.code_house.webconsole.core.api.plugin.Plugin;
import org.code_house.webconsole.core.common.ExtensionImpl;

/**
 * Implementation of plugin.
 * 
 * @author luke@code-house.org
 */
public class PluginImpl extends ExtensionImpl implements Plugin {

    /**
     * Vendor name.
     */
    private String vendor;

    /**
     * Plugin name.
     */
    private String name;

    /**
     * Plugin url.
     */
    private String url;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
