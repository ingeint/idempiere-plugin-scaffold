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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.webui.factory.IFormFactory;
import org.adempiere.webui.panel.ADForm;
import org.compiere.util.CLogger;

public abstract class CustomFormFactory implements IFormFactory {

	private final static CLogger log = CLogger.getCLogger(CustomProcessFactory.class);
	private List<Class<? extends CustomFormController>> cacheForm = new ArrayList<Class<? extends CustomFormController>>();

	/**
	 * For initialize class. Register the custom forms to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerForm(FPrintPluginInfo.class);
	 * }
	 * </pre>
	 */
	protected abstract void initialize();

	/**
	 * Register process
	 * 
	 * @param processClass
	 *            Process class to register
	 */
	protected void registerForm(Class<? extends CustomFormController> formClass) {
		cacheForm.add(formClass);
	}

	/**
	 * Default constructor
	 */
	public CustomFormFactory() {
		initialize();
	}

	@Override
	public ADForm newFormInstance(String formName) {
		for (int i = 0; i < cacheForm.size(); i++) {
			if (formName.equals(cacheForm.get(i).getName())) {
				try {
					CustomFormController customForm = cacheForm.get(i).newInstance();
					log.info(String.format("FormFactory [Class Name: %s]", formName));
					ADForm adForm = customForm.getForm();
					adForm.setICustomForm(customForm);
					return adForm;
				} catch (Exception e) {
					log.severe(String.format("FormFactory [Class %s can not be instantiated, Exception: %s]", formName, e));
					return null;
				}
			}
		}
		return null;
	}

}
