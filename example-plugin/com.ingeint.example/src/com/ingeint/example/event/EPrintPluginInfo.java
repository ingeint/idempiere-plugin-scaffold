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

package com.ingeint.example.event;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.annotation.ModelEventTopic;
import org.adempiere.base.event.annotations.ModelEventDelegate;
import org.adempiere.base.event.annotations.doc.AfterComplete;
import org.compiere.util.CLogger;
import org.osgi.service.event.Event;

import com.ingeint.example.base.BundleInfo;
import com.ingeint.example.model.MTableDocExample;

/**
 * Event example
 * https://wiki.idempiere.org/en/NF9_OSGi_New_Event_Handling_Annotation
 */
@EventTopicDelegate
@ModelEventTopic(modelClass = MTableDocExample.class)
public class EPrintPluginInfo extends ModelEventDelegate<MTableDocExample> {

	private final static CLogger log = CLogger.getCLogger(EPrintPluginInfo.class);

	public EPrintPluginInfo(MTableDocExample po, Event event) {
		super(po, event);
	}

	@AfterComplete
	protected void doAfterComplete() {
		MTableDocExample docTemplate = getModel();
		log.info(docTemplate.toString());
		log.info("Plugin: %s, Plugin ID: %s".formatted(BundleInfo.getBundleName(), BundleInfo.getBundleID()));
	}

}
