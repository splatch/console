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

import org.code_house.webconsole.core.api.Extension;
import org.code_house.webconsole.runtime.registry.ExtensionRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Service tracker which registers new extensions to {@link ExtensionRegistry}.
 * 
 * @author luke@code-house.org
 */
public class ExtensionTracker implements ServiceTrackerCustomizer {

    /**
     * Extension registry.
     */
    private OsgiExtensionRegistry registry;

    /**
     * Bundle context.
     */
    private BundleContext context;

    /**
     * Service tracker which reports new services to {@link OsgiExtensionRegistry}.
     * 
     * @param registry Extension registry.
     * @param context Bundle context.
     */
    public ExtensionTracker(OsgiExtensionRegistry registry, BundleContext context) {
        this.registry = registry;
        this.context = context;
    }

    public Object addingService(ServiceReference reference) {
        Object service = context.getService(reference);
        System.out.println("=>" + service.getClass());
        System.out.println("=>" + service.getClass().getInterfaces());

        if (service instanceof Extension) {
            return registry.registerExtension((String) reference.getProperty("extensionType"),
                (Extension) service);
        }
        return null;
    }

    public void modifiedService(ServiceReference reference, Object service) {
        
    }

    public void removedService(ServiceReference reference, Object service) {
        if (service instanceof ExtensionRegistration) {
            registry.unregisterExtension((ExtensionRegistration) service);
        }
    }

}
