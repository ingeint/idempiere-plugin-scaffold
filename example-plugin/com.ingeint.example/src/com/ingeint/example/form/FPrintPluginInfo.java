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

package com.ingeint.example.form;

import org.adempiere.webui.editor.WStringEditor;
import org.idempiere.ui.zk.annotation.Form;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;

import com.ingeint.example.base.BundleInfo;
import com.ingeint.example.base.CustomForm;

/**
 * Form example
 * https://wiki.idempiere.org/en/NF9_OSGi_New_Form_Factory
 */
@Form
public class FPrintPluginInfo extends CustomForm {

	private static final long serialVersionUID = 9149161111834091759L;
	private Button processButton;
	private WStringEditor pluginInfo;

	@Override
	protected void initForm() {
		Borderlayout mainLayout = new Borderlayout();
		appendChild(mainLayout);

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
			pluginInfo.getComponent().setText(
					"Plugin: %s, Plugin ID: %s".formatted(BundleInfo.getBundleName(), BundleInfo.getBundleID()));
		}
	}

}
