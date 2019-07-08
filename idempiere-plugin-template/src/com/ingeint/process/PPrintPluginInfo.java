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
 * Copyright (C) {year} INGEINT <http://www.ingeint.com>.
 * Copyright (C) Contributors.
 * 
 * Contributors:
 *    - {year} {name of contributor} <{email}>.
 */

package com.ingeint.process;

import com.ingeint.base.BundleInfo;
import com.ingeint.base.CustomProcess;

/**
 * Process example
 */
public class PPrintPluginInfo extends CustomProcess {

	@Override
	protected void prepare() {

	}

	@Override
	protected String doIt() throws Exception {
		// For error throw a AdempiereException
		// throw new AdempiereException("Test Error From PPrintPluginInfo");
		StringBuilder builder = new StringBuilder();

		BundleInfo bundleInfo = BundleInfo.getInstance();
		builder.append("ID: " + bundleInfo.getBundleID());
		builder.append(" ");
		builder.append("Name: " + bundleInfo.getBundleName());
		builder.append(" ");
		builder.append("Vendor: " + bundleInfo.getBundleVendor());
		builder.append(" ");
		builder.append("Version: " + bundleInfo.getBundleVersion());

		return builder.toString();
	}
}
