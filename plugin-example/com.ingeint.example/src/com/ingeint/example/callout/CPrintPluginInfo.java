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

package com.ingeint.example.callout;

import org.adempiere.base.annotation.Callout;
import org.compiere.util.CLogger;

import com.ingeint.example.base.BundleInfo;
import com.ingeint.example.base.CustomCallout;
import com.ingeint.example.model.I_TL_TableExample;

/**
 * Callout example
 * https://wiki.idempiere.org/en/NF9_OSGi_New_Column_Callout_Factory
 */
@Callout(tableName = I_TL_TableExample.Table_Name, columnName = I_TL_TableExample.COLUMNNAME_Description)
public class CPrintPluginInfo extends CustomCallout {

	private final static CLogger log = CLogger.getCLogger(CPrintPluginInfo.class);

	@Override
	protected String start() {
		String value = (String) getValue();

		if (value == null)
			return null;

		log.info(value);

		setValue("Plugin: %s, Plugin ID: %s".formatted(BundleInfo.getBundleName(), BundleInfo.getBundleID()));

		return null;
	}
}
