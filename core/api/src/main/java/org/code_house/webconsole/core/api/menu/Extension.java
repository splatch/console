package org.code_house.webconsole.core.api.menu;

import org.code_house.webconsole.core.api.resource.Resource;

/**
 * Extension in menu.
 * 
 * @author ldywicki
 */
public interface Extension {

    String getLabel();

    String getHref();

    String getAlt();

    /**
     * Returns image for menu element.
     * 
     * @return
     */
    Resource getImageResource();

}
