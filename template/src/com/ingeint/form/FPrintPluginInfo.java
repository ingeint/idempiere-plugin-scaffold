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

package com.ingeint.form;

import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.panel.CustomForm;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ingeint.base.BundleInfo;
import com.ingeint.base.CustomFormController;

/**
 * This is a example of custom form
 */
public class FPrintPluginInfo extends CustomFormController {

	private Button processButton;
	private WStringEditor pluginInfo;

	@Override
	protected void buildForm() throws Exception {
		CustomForm form = (CustomForm) getForm();

		Borderlayout mainLayout = new Borderlayout();
		form.appendChild(mainLayout);

		North mainPanelNorth = new North();
		mainLayout.appendChild(mainPanelNorth);

		Center mainPanelCenter = new Center();
		mainLayout.appendChild(mainPanelCenter);

		processButton = new Button();
		processButton.setLabel("Print Plugin Info");
		processButton.setStyle("text-align: right");
		mainPanelNorth.appendChild(processButton);

		pluginInfo = new WStringEditor();
		pluginInfo.getComponent().setText("");
		pluginInfo.getComponent().setWidth("100%");
		pluginInfo.setReadWrite(true);
		mainPanelCenter.appendChild(pluginInfo.getComponent());

		processButton.addEventListener(Events.ON_CLICK, this);

	}

	@Override
	public void onEvent(Event event) throws Exception {
		if (event.getTarget().equals(processButton)) {
			BundleInfo bundleInfo = BundleInfo.getInstance();
			pluginInfo.getComponent().setText(bundleInfo.getBundleID() + " " + bundleInfo.getBundleName() + " " + bundleInfo.getBundleVendor());
		}
	}

}
