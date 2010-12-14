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
package org.code_house.webconsole.runtime.registry.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.code_house.webconsole.core.api.Extension;
import org.code_house.webconsole.core.api.ExtensionPoint;
import org.code_house.webconsole.runtime.registry.MissingExtensionException;

/**
 * Implementation of extension point.
 * 
 * @author luke@code-house.org
 */
@SuppressWarnings("rawtypes")
public class OsgiExtensionPoint implements ExtensionPoint {

    /**
     * Type of extension.
     */
    private String type;

    /**
     * Map extension type - extensions.
     */
    private Map<String, Set<Extension>> extensionMap = new HashMap<String, Set<Extension>>();

    public OsgiExtensionPoint(String type, List<String> types) {
        this.type = type;

        for (String extType : types) {
            extensionMap.put(extType, new HashSet<Extension>());
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Extension> getExtensions() {
        Set<Extension> extensions = new LinkedHashSet<Extension>();
        for (Set<Extension> extensionList : extensionMap.values()) {
            extensions.addAll(extensionList);
        }
        return extensions;
    }

    public void addExtension(String type, Extension extension) {
        if (isSupported(type, true)) {
            extensionMap.get(type).add(extension);
        }
    }

    public void removeExtension(String type, Extension extension) {
        if (isSupported(type, true)) {
            extensionMap.get(type).remove(extension);
        }
    }

    public Set<Extension> getExtensions(Class type) {
        return extensionMap.get(type.getName());
    }

    public List<Class> getExtensionTypes() {
        return new ArrayList(extensionMap.keySet());
    }

    public boolean isSupported(String type, boolean required) {
        if (extensionMap.containsKey(type)) {
            return true;
        }
        if (required) {
            // missing extension type - let throw an exception
            throw new MissingExtensionException("Extension of type " + type 
                + " is not supported by extension point " + getType());
        }
        return false;
    }

}
