package org.code_house.webconsole.runtime.registry.internal;

import java.util.List;

import org.code_house.webconsole.core.api.Extension;
import org.code_house.webconsole.core.api.ExtensionPoint;

public class ExtensionRegistration {

    private final String type;
    private final List<ExtensionPoint> extensionPoints;
    private final Extension extension;

    public ExtensionRegistration(String type, Extension extension,
        List<ExtensionPoint> extensionPoints) {
        this.type = type;
        this.extension = extension;
        this.extensionPoints = extensionPoints;
    }

    public String getType() {
        return type;
    }

    public List<ExtensionPoint> getExtensionPoints() {
        return extensionPoints;
    }

    public Extension getExtension() {
        return extension;
    }

    

}
