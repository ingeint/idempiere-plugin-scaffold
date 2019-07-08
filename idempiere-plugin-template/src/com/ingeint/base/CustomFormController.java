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
 * Copyright (C) 2015 INGEINT <http://www.ingeint.com>.
 * Copyright (C) Contributors.
 * 
 * Contributors:
 *    - 2015 Saúl Piña <spina@ingeint.com>.
 */

package com.ingeint.base;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 * Custom class for forms
 */
public abstract class CustomFormController implements IFormController, EventListener<Event> {

	private CustomForm form;

	@Override
	public ADForm getForm() {
		return form;
	}

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public CustomFormController() {
		form = new CustomForm();
		try {
			buildForm();
		} catch (Exception e) {
			throw new AdempiereException("Error building form: " + e.getMessage(), e);
		}
	}

	/**
	 * For build form
	 * 
	 * @throws Exception
	 */
	protected abstract void buildForm() throws Exception;

}
