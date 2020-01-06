/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2020 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.template.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * This util allows you to create complex files using velocity framework
 *
 * @see <a href="https://velocity.apache.org/">Velocity</a> Dependency:
 *      org.apache.velocity:velocity-engine-core:2.1 Example: String result =
 *      FileTemplateBuilder.builder().template("template.xml").inject("invoice",
 *      new Invoice()).export("text.xml");
 */
public class FileTemplateBuilder {

	private final VelocityEngine engine;
	private final VelocityContext context;
	private Template template;

	private FileTemplateBuilder() {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();

		context = new VelocityContext();
	}

	/**
	 * Creates a new builder
	 *
	 * @return New builder object
	 */
	public static FileTemplateBuilder builder() {
		return new FileTemplateBuilder();
	}

	/**
	 * This method adds new object to the template
	 *
	 * @param name   Object name
	 * @param object Object to be injected
	 * @return Current builder
	 */
	public FileTemplateBuilder inject(String name, Object object) {
		context.put(name, object);
		return this;
	}

	/**
	 * Adds the template's path
	 *
	 * @param path Template path
	 * @return Current builder
	 */
	public FileTemplateBuilder file(String path) {
		template = engine.getTemplate(path);
		template.setEncoding(StandardCharsets.UTF_8.displayName());
		return this;
	}

	/**
	 * Build the file as a string
	 *
	 * @return File string
	 */
	public String build() {
		if (template == null) {
			return "";
		}

		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}

	/**
	 * Exports the template to a file
	 *
	 * @param path Path to save the file
	 * @return File string
	 * @throws IOException When throws a error writing the file
	 */
	public String export(String path) throws IOException {
		if (template != null) {
			try (FileWriter writer = new FileWriter(path)) {
				template.merge(context, writer);
			}
		}

		return build();
	}

}
