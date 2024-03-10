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
 * Copyright (C) 2024 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.example.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * This util allows you to create clean file using freemarker framework
 *
 * @see <a href="https://freemarker.apache.org/">freemarker</a>
 * 
 *      Dependency: org.freemarker:freemarker:2.3.29 Example: String file =
 *      FileTemplateBuilder.builder().template("template").inject("invoice", new
 *      Invoice()).export("text.xml");
 */
public class FileTemplateBuilder {

	private Configuration engine;
	private Template template;
	private Map<String, Object> context;

	private FileTemplateBuilder(Class<?> contextClass) {
		engine = new Configuration(Configuration.VERSION_2_3_29);
		engine.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		engine.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		engine.setLogTemplateExceptions(false);
		engine.setWrapUncheckedExceptions(true);
		engine.setFallbackOnNullLoopVariable(false);
		engine.setClassLoaderForTemplateLoading(contextClass.getClassLoader(), "/");
		context = new HashMap<String, Object>();
	}

	/**
	 * Creates a new builder
	 *
	 * @return New builder object
	 */
	public static FileTemplateBuilder builder() {
		return new FileTemplateBuilder(FileTemplateBuilder.class);
	}

	/**
	 * Set the class context loader
	 * 
	 * @param contextClass
	 * @return Builder object
	 */
	public static FileTemplateBuilder builder(Class<?> contextClass) {
		return new FileTemplateBuilder(contextClass);
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
		try {
			template = engine.getTemplate(path);
		} catch (IOException e) {
			throw new AdempiereException(e);
		}
		return this;
	}

	/**
	 * Build a file as a string
	 *
	 * @return String
	 */
	public String build() {
		if (template == null) {
			return "";
		}

		StringWriter writer = new StringWriter();
		try {
			template.process(context, writer);
		} catch (TemplateException | IOException e) {
			throw new AdempiereException(e);
		}
		return writer.toString();
	}

	/**
	 * Exports to a file
	 *
	 * @param path Path to save the file
	 * @return String file
	 */
	public String export(String path) {
		if (template != null) {
			try (FileWriter writer = new FileWriter(path)) {
				template.process(context, writer);
			} catch (TemplateException | IOException e) {
				throw new AdempiereException(e);
			}
		}
		return build();
	}

}
