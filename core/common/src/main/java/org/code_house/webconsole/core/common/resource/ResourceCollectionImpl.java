package org.code_house.webconsole.core.common.resource;

import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import org.code_house.webconsole.core.api.resource.ResourceCollection;
import org.code_house.webconsole.core.common.ExtensionImpl;
import org.osgi.framework.Bundle;

public class ResourceCollectionImpl extends ExtensionImpl implements
    ResourceCollection {

    private String location;
    private Bundle bundle;
    private String aliasPrefix;

    @SuppressWarnings("unchecked")
    public Map<String, URL> getLocations() {
        Enumeration<URL> paths = bundle.findEntries(location, "*", true);

        Map<String, URL> treeMap = new TreeMap<String, URL>();

        while (paths != null && paths.hasMoreElements()) {
            URL path = paths.nextElement();
            treeMap.put(getAliasPrefix() + path.getPath(), path);
        }

        return treeMap;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public String getAliasPrefix() {
        return aliasPrefix == null ? "" : aliasPrefix;
    }

    public void setAliasPrefix(String aliasPrefix) {
        this.aliasPrefix = aliasPrefix;
    }

}
