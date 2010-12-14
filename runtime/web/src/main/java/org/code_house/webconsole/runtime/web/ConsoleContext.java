package org.code_house.webconsole.runtime.web;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.code_house.webconsole.core.api.resource.Resource;
import org.code_house.webconsole.core.api.resource.ResourceCollection;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

public class ConsoleContext implements HttpContext {

	private List<Resource> resources;
	private List<ResourceCollection> collections;
	private final HttpService service;

	public ConsoleContext(HttpService service) throws NamespaceException {
		this.service = service;
	}

	public boolean handleSecurity(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		return true;
	}

	public URL getResource(String name) {
		for (Resource resource : resources) {
			if (resource.getLocation().getPath().contains(name)) {
				return resource.getLocation();
			}
		}

		for (ResourceCollection collection : collections) {
//			Enumeration<URL> locations = collection.getLocations();
//			while (locations != null && locations.hasMoreElements()) {
//				URL nextElement = locations.nextElement();
//				if (nextElement.getPath().contains(name)) {
//					return nextElement;
//				}
//			}
		}
		return null;
	}

	public String getMimeType(String name) {
		return null;
	}

	// 
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public void setCollections(List<ResourceCollection> collections) {
		this.collections = collections;
	}

	public void start() throws NamespaceException {
		service.registerResources("/static", "/", this);
	}

	public void stop() {
		service.unregister("/");
	}
}
