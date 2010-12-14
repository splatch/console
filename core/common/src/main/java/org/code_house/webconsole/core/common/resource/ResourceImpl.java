package org.code_house.webconsole.core.common.resource;

import java.net.URL;

import org.code_house.webconsole.core.api.resource.Resource;
import org.code_house.webconsole.core.common.ExtensionImpl;
import org.osgi.framework.Bundle;

public class ResourceImpl extends ExtensionImpl implements Resource {

    private String location;
    private Bundle bundle;
    private String alias;

    public String getAlias() {
        return alias;
    }

    public URL getLocation() {
        return bundle.getEntry(location);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
