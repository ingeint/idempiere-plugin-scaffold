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

package com.ingeint.event;

import java.io.IOException;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CLogger;

import com.ingeint.base.BundleInfo;
import com.ingeint.base.CustomEvent;
import com.ingeint.model.MTableDocTemplate;

/**
 * This is a example of Event
 */
public class EPrintPluginInfo extends CustomEvent {

	private final static CLogger log = CLogger.getCLogger(EPrintPluginInfo.class);

	@Override
	protected void doHandleEvent() {
		MTableDocTemplate docTemplate = (MTableDocTemplate) getPO();
		log.info(docTemplate.toString());

		BundleInfo bundleInfo;

		try {
			bundleInfo = BundleInfo.getInstance();
			log.info(bundleInfo.toString());
		} catch (IOException e) {
			throw new AdempiereException("Error in BundleInfo", e);
		}

		throw new AdempiereException(bundleInfo.toString());
	}

}
