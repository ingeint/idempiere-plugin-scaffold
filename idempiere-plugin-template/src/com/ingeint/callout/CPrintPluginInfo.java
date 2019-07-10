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

package com.ingeint.callout;

import java.io.IOException;

import org.compiere.util.CLogger;

import com.ingeint.base.BundleInfo;
import com.ingeint.base.CustomCallout;
import com.ingeint.model.MTableDocTemplate;

/**
 * This is a example of Callout
 */
public class CPrintPluginInfo extends CustomCallout {

	private final static CLogger log = CLogger.getCLogger(CPrintPluginInfo.class);

	@Override
	protected String start() {
		String value = (String) getTab().getValue(MTableDocTemplate.COLUMNNAME_Description);

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

		getTab().setValue(MTableDocTemplate.COLUMNNAME_Description, bundleInfo.toString());

		return null;
	}
}
