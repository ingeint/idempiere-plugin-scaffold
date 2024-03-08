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
 * Copyright (C) 2015 INGEINT <http://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.example.process;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.base.annotation.Process;

import com.ingeint.example.base.BundleInfo;
import com.ingeint.example.base.CustomProcess;

/**
 * Process example
 * https://wiki.idempiere.org/en/NF9_OSGi_New_Process_Factory
 * https://wiki.idempiere.org/en/NF9_Annotated_Process_Parameters
 */
@Process
public class PPrintPluginInfo extends CustomProcess {

	@Parameter
	private String name;

	@Override
	protected String doIt() throws Exception {
		return "Hello %s, this is the '%s'".formatted(name, BundleInfo.getBundleName());
	}
}
