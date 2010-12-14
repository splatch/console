package org.code_house.webconsole.runtime.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.code_house.webconsole.core.api.ExtensionPoint;
import org.code_house.webconsole.runtime.registry.ExtensionRegistry;

@SuppressWarnings("all") 
public class LaunchServlet extends HttpServlet {

	private ExtensionRegistry registry;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();
		writer.write("Extension Type\tExtensions\tURL\n");
		for (ExtensionPoint ep : registry.getExtensionPoints()) {
			writer.write(
				ep.getType() + "\t" + ep.getExtensions() + "\n"
			);
		}
	}

    public void setExtensionRegistry(ExtensionRegistry registry) {
		this.registry = registry;
	}

}
