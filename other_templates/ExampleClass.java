/**
 * This file is part of iDempiere ERP <http://www.idempiere.org>.
 * 
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
 * Copyright (C) 2015 INGEINT <http://www.ingeint.com>.
 * Copyright (C) Contributors.
 * 
 * Contributors:
 *    - 2015 Saúl Piña <spina@ingeint.com>.
 */

package com.ingeint.example;

/**
 * This is a example class for INGEINT template
 */
public class ExampleClass {

	/**
	 * This is a example constant for INGEINT template
	 */
	public static final int EXAMPLE_CONST = 1;

	/**
	 * This is a attribute for this template
	 */
	private String value;

	/**
	 * This is a example method for INGEINT template
	 * 
	 * @return The value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * This is a example method for INGEINT template
	 * 
	 * <pre>
	 * public static void main(String[] args) {
	 * 	ExampleClass e = new ExampleClass();
	 * 	e.setValue(&quot;example&quot;);
	 * }
	 * </pre>
	 * 
	 * @param value
	 *            Example value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
