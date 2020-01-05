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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * This util allows you to create clean xml file using velocity framework
 *
 * @see <a href="https://velocity.apache.org/">Velocity</a>
 * Dependency: org.apache.velocity:velocity-engine-core:2.1
 * Example: String xml = XmlTemplateBuilder.builder().template("template").inject("invoice", new Invoice()).export("text.xml");
 */
public class XmlTemplateBuilder {

    private static final String XML_TEMPLATE_PATH_FORMAT = "xml/%s.xml";
    private final VelocityEngine engine;
    private final VelocityContext context;
    private Template template;

    private XmlTemplateBuilder() {
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
    public static XmlTemplateBuilder builder() {
        return new XmlTemplateBuilder();
    }

    /**
     * This method adds new object to the template
     *
     * @param name   Object name
     * @param object Object to be injected
     * @return Current builder
     */
    public XmlTemplateBuilder inject(String name, Object object) {
        context.put(name, object);
        return this;
    }

    /**
     * This method looking for a xml template in the xml folder.
     * For example:  xml/invoice.xml, the parameter would be invoice.
     *
     * @param name Template name
     * @return Current bilder
     */
    public XmlTemplateBuilder template(String name) {
        return file(String.format(XML_TEMPLATE_PATH_FORMAT, name));
    }

    /**
     * Adds the template's path
     *
     * @param path Template path
     * @return Current builder
     */
    public XmlTemplateBuilder file(String path) {
        template = engine.getTemplate(path);
        template.setEncoding(StandardCharsets.UTF_8.displayName());
        return this;
    }

    /**
     * Build the xml file as a string
     *
     * @return Xml string
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
     * Exports the xml to a file
     *
     * @param path Path to save the xml file
     * @return Xml string
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
