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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * This util allows you create sql string in a simple way.
 * 
 * Example: String sql = SqlBuilder.builder().template("read-bpartner").build();
 */
public class SqlBuilder {

	private static final String SQL_TEMPLATE_PATH_FORMAT = "sql/%s.sql";
	private StringBuilder sql = new StringBuilder();

	private SqlBuilder() {

	}

	/**
	 * Creates a new builder
	 * 
	 * @return New builder object
	 */
	public static SqlBuilder builder() {
		return new SqlBuilder();
	}

	/**
	 * This method looking for a sql template in the sql folder. For example:
	 * sql/read-bpartner.sql, the parameter would be read-bpartner.
	 * For comments in the sql file use "--" symbol.
	 * 
	 * @param name Sql template name
	 * @return Current builder
	 * @throws IOException When throws a error reading the template
	 */
	public SqlBuilder template(String name) throws IOException {
		return file(String.format(SQL_TEMPLATE_PATH_FORMAT, name));
	}

	/**
	 * Adds a a sql file to sql string.
	 * For comments in the sql file use "--" symbol.
	 * 
	 * @param path Sql path inside plugin
	 * @return Current builder
	 * @throws IOException When throws a error reading the file
	 */
	public SqlBuilder file(String path) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				statement(line);
			}
		}
		return this;
	}

	/**
	 * Adds a statement line to the current sql builder
	 * 
	 * @param statement A string sql
	 * @return Current builder
	 */
	public SqlBuilder statement(String statement) {
		statement = statement.trim();
		if (!statement.startsWith("--")) {
			sql.append(statement).append(" ");
		}
		return this;
	}

	/**
	 * Concats the whole path set
	 * 
	 * @return Sql string
	 */
	public String build() {
		return sql.toString().trim();
	}
}
