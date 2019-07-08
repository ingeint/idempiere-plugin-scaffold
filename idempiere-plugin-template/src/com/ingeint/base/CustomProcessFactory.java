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

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;
import org.compiere.util.CLogger;

/**
 * Dynamic process factory
 */
public abstract class CustomProcessFactory implements IProcessFactory {

	private final static CLogger log = CLogger.getCLogger(CustomProcessFactory.class);
	private List<Class<? extends CustomProcess>> cacheProcess = new ArrayList<Class<? extends CustomProcess>>();

	/**
	 * For initialize class. Register the process to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerProcess(PPrintPluginInfo.class);
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
	protected void registerProcess(Class<? extends CustomProcess> processClass) {
		cacheProcess.add(processClass);
	}

	/**
	 * Default constructor
	 */
	public CustomProcessFactory() {
		initialize();
	}

	@Override
	public ProcessCall newProcessInstance(String className) {
		for (int i = 0; i < cacheProcess.size(); i++) {
			if (className.equals(cacheProcess.get(i).getName())) {
				try {
					CustomProcess customProcess = cacheProcess.get(i).newInstance();
					log.info(String.format("ProcessFactory [Class Name: %s]", className));
					return customProcess;
				} catch (Exception e) {
					log.severe(String.format("ProcessFactory [Class %s can not be instantiated, Exception: %s]", className, e));
					return null;
				}
			}
		}
		return null;
	}

}
