package org.code_house.webconsole.core.common;

import org.code_house.webconsole.core.api.Extension;

/**
 * Default implementation of the extension.
 * 
 * @author luke@code-house.org
 */
public class ExtensionImpl implements Extension {

	/**
	 * Extension id.
	 */
	private String id;

	/**
	 * {@inheritDoc}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets extension id.
	 * 
	 * @param id Extension id.
	 */
	public void setId(String id) {
		this.id = id;
	}


}
