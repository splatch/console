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
package org.code_house.webconsole.runtime.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.code_house.webconsole.core.api.Extension;
import org.code_house.webconsole.core.api.ExtensionPoint;
import org.code_house.webconsole.core.api.resource.Resource;
import org.code_house.webconsole.core.api.resource.ResourceCollection;
import org.code_house.webconsole.runtime.registry.ExtensionRegistry;

/**
 * Web console ships own resource servlet.
 * 
 * @author luke@code-house.org
 */
@SuppressWarnings("all")
public class ResourceServlet extends HttpServlet {

    private ExtensionRegistry registry;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String path = req.getPathInfo();

        ExtensionPoint extensionPoint = registry.getExtensionPoint("org.code_house.webconsole.core.api.resource");

        // static resource mapping have higher priority over resource collection
        Set<Extension> extensions = extensionPoint.getExtensions(Resource.class);
        for (Extension extension : extensions) {
            Resource resource = (Resource) extension;
            if (path.matches(resource.getAlias())) {
                streamResource(resource.getLocation(), resp);
                return;
            }
        }

        extensions = extensionPoint.getExtensions(ResourceCollection.class);
        for (Extension extension : extensions) {
            ResourceCollection collection = (ResourceCollection) extension;
            Map<String, URL> locations = collection.getLocations();
            if (locations.containsKey(path)) {
                streamResource(locations.get(path), resp);
                break;
            }
        }
    }

    private void streamResource(URL url, ServletResponse resp) throws IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        InputStream stream = url.openStream();
        try {
            byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } finally {
            stream.close();
            outputStream.flush();
        }
    }

    public void setExtensionRegistry(ExtensionRegistry registry) {
        this.registry = registry;
    }

}
