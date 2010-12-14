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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.code_house.webconsole.runtime.registry.ExtensionRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * Service tracker which registers new extensions to {@link ExtensionRegistry}.
 * 
 * @author luke@code-house.org
 */
public class ExtensionPointTracker implements BundleTrackerCustomizer {

    /**
     * Extension registry.
     */
    private OsgiExtensionRegistry registry;

    public ExtensionPointTracker(OsgiExtensionRegistry registry) {
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public Object addingBundle(Bundle bundle, BundleEvent event) {
        List<String> registrations = getExtensionPoints(bundle);
        if (!registrations.isEmpty()) {
            return registrations;
        }
        return null;
    }

    private List<String> getExtensionPoints(Bundle bundle) {
        Enumeration<String> entry = bundle.getEntryPaths("/OSGI-INF/extension");
        List<String> registrations = new ArrayList<String>();
        while (entry != null && entry.hasMoreElements()) {
            Properties properties = new Properties();
            try {
                String element = entry.nextElement();
                properties.load(bundle.getEntry(element).openStream());
                String id = element.substring(element.lastIndexOf('/') + 1);
                registry.addExtensionPoint(id, properties);
                registrations.add(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return registrations;
    }

    public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
        List<String> ids = (List<String>) object;
        ids.clear();
        ids.addAll(getExtensionPoints(bundle));
    }

    public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
        List<String> ids = (List<String>) object;
        for (String id : ids) {
            registry.removeExtensionPoint(id);
        }
    }

}
