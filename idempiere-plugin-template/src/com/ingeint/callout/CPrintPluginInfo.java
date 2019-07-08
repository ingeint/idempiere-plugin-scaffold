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

package com.ingeint.callout;

import java.io.IOException;

import org.compiere.util.CLogger;

import com.ingeint.base.BundleInfo;
import com.ingeint.base.CustomCallout;
import com.ingeint.model.MTableExample;

/**
 * This is a example of Callout
 */
public class CPrintPluginInfo extends CustomCallout {

	private final static CLogger log = CLogger.getCLogger(CPrintPluginInfo.class);

	@Override
	protected String start() {
		String value = (String) getTab().getValue(MTableExample.COLUMNNAME_Text);

		if (value == null)
			return null;
		log.info(value);

		BundleInfo bundleInfo;
		try {
			bundleInfo = BundleInfo.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in BundleInfo";
		}

		getTab().setValue(MTableExample.COLUMNNAME_Text, bundleInfo.getBundleID());

		return null;
	}
}
