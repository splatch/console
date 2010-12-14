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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.code_house.webconsole.core.api.Extension;
import org.code_house.webconsole.core.api.ExtensionPoint;
import org.code_house.webconsole.runtime.registry.DuplicatedExtensionPointException;
import org.code_house.webconsole.runtime.registry.ExtensionRegistry;
import org.code_house.webconsole.runtime.registry.MissingExtensionPointException;

/**
 * Implementation of extension registry based on OSGi service registry.
 * 
 * @author luke@code-house.org
 */
public class OsgiExtensionRegistry implements ExtensionRegistry {

    /**
     * Hash map to store extension points.
     */
    private Map<String, OsgiExtensionPoint> extensionPoints = new TreeMap<String,
        OsgiExtensionPoint>();

    /**
     * {@inheritDoc}
     */
    public List<ExtensionPoint> getExtensionPoints() {
        return new ArrayList<ExtensionPoint>(extensionPoints.values());
    }

    /**
     * {@inheritDoc}
     */
    public ExtensionPoint getExtensionPoint(String type) {
        if (isRegistered(type)) {
            return extensionPoints.get(type);
        }
        throw new MissingExtensionPointException("Extension point " + type
            + " is not available in registry");
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRegistered(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Expected type of extension point, null given");
        }
        return extensionPoints.containsKey(type);
    }

    // internal methods 
    public ExtensionRegistration registerExtension(String type, Extension extension) {
        List<ExtensionPoint> list = new ArrayList<ExtensionPoint>();
        for (OsgiExtensionPoint extensionPoint : extensionPoints.values()) {
            if (extensionPoint.isSupported(type, false)) {
                extensionPoint.addExtension(type, extension);
                list.add(extensionPoint);
            }
        }

        if (list.isEmpty()) {
            System.err.println("Extension of " + type + " doesn't match any extension point definition");
        }
        return new ExtensionRegistration(type, extension, list);
    }

    public void addExtensionPoint(String type, Properties properties) {
        if (isRegistered(type)) {
            throw new DuplicatedExtensionPointException("Extension point of type"
                + type + " is already registered");
        }

        List<String> types = new ArrayList<String>();
        for (Object key : properties.keySet()) {
            types.add(key.toString());
        }
        extensionPoints.put(type, new OsgiExtensionPoint(type, types));
    }

    public void unregisterExtension(ExtensionRegistration registration) {
        for (ExtensionPoint extensionPoint : registration.getExtensionPoints()) {
            ((OsgiExtensionPoint) extensionPoint).removeExtension(registration.getType(),
                registration.getExtension());
        }
    }

    public void removeExtensionPoint(String type) {
        extensionPoints.remove(type);
    }

}
