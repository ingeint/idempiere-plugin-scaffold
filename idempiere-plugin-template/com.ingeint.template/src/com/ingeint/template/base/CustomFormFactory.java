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
 * Copyright (C) 2020 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.template.base;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.factory.IFormFactory;
import org.adempiere.webui.panel.ADForm;
import org.compiere.util.CLogger;

/**
 * Dynamic form factory
 */
public abstract class CustomFormFactory implements IFormFactory {

	private final static CLogger log = CLogger.getCLogger(CustomFormFactory.class);
	private List<Class<? extends CustomForm>> cacheForm = new ArrayList<Class<? extends CustomForm>>();

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
	 * @param processClass Process class to register
	 */
	protected void registerForm(Class<? extends CustomForm> formClass) {
		cacheForm.add(formClass);
		log.info(String.format("CustomForm registered -> %s", formClass.getName()));
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
					CustomForm customForm = cacheForm.get(i).getConstructor().newInstance();
					log.info(String.format("CustomForm created -> %s", formName));
					ADForm adForm = customForm.getForm();
					adForm.setICustomForm(customForm);
					return adForm;
				} catch (Exception e) {
					log.severe(String.format("Class %s can not be instantiated, Exception: %s", formName, e));
					throw new AdempiereException(e);
				}
			}
		}
		return null;
	}

}
