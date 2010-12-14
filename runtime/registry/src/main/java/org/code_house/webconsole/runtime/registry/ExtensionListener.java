package org.code_house.webconsole.runtime.registry;

import org.code_house.webconsole.core.api.Extension;

/**
 * Listener
 * 
 * @author luke@code-house.org
 */
public interface ExtensionListener {

    void extensionRegistered(Extension e);

    void extensionUnregistered(Extension e);

}
