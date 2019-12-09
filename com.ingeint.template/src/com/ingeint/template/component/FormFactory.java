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
 * Copyright (C) 2019 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.template.component;

import com.ingeint.template.base.CustomFormFactory;
import com.ingeint.template.form.FPrintPluginInfo;

/**
 * Form Factory
 */
public class FormFactory extends CustomFormFactory {

	/**
	 * For initialize class. Register the custom forms to build. This method is
	 * useful when is not using autoscan feature.
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerForm(FPrintPluginInfo.class);
	 * }
	 * </pre>
	 */
	@Override
	protected void initialize() {
		registerForm(FPrintPluginInfo.class);
	}

}
