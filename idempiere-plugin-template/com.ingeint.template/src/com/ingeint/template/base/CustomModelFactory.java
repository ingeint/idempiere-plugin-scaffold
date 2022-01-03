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
 * Copyright (C) 2021 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.template.base;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Properties;

import org.adempiere.base.IModelFactory;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * Dynamic model factory
 */
public abstract class CustomModelFactory implements IModelFactory {

	private final static CLogger log = CLogger.getCLogger(CustomModelFactory.class);
	private Hashtable<String, Class<?>> cacheModels = new Hashtable<String, Class<?>>();

	/**
	 * For initialize class. Register the models to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerTableModel(MTableExample.Table_Name, MTableExample.class);
	 * }
	 * </pre>
	 */
	protected abstract void initialize();

	/**
	 * Register the models of plugin
	 * 
	 * @param tableName  Table name
	 * @param tableModel Model of the table
	 */
	protected void registerModel(String tableName, Class<? extends PO> tableModel) {
		cacheModels.put(tableName, tableModel);
		log.info(String.format("Model registered -> %s [Table Name: %s]", tableModel.getName(), tableName));
	}

	/**
	 * Default constructor
	 */
	public CustomModelFactory() {
		initialize();
	}

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName == null)
			return null;
		Class<?> clazz = cacheModels.get(tableName);
		return clazz;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {

		Class<?> clazz = getClass(tableName);
		if (clazz == null)
			return null;

		PO model = null;
		Constructor<?> constructor = null;

		try {
			constructor = clazz.getDeclaredConstructor(new Class[] { Properties.class, int.class, String.class });
			model = (PO) constructor.newInstance(new Object[] { Env.getCtx(), Record_ID, trxName });
			log.info(String.format("Model created -> %s [Table Name: %s]", clazz.getName(), tableName));
		} catch (Exception e) {
			log.severe(String.format("Class %s can not be instantiated for table: %s, Exception: %s", clazz.getName(), tableName, e));
			throw new AdempiereException(e);
		}

		return model;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {

		Class<?> clazz = getClass(tableName);
		if (clazz == null)
			return null;

		PO model = null;
		Constructor<?> constructor = null;

		try {
			constructor = clazz.getDeclaredConstructor(new Class[] { Properties.class, ResultSet.class, String.class });
			model = (PO) constructor.newInstance(new Object[] { Env.getCtx(), rs, trxName });
			log.info(String.format("Model created -> %s [Table Name: %s]", clazz.getName(), tableName));
		} catch (Exception e) {
			log.severe(String.format("Class %s can not be instantiated for table: %s, Exception: %s", clazz.getName(), tableName, e));
			throw new AdempiereException(e);
		}

		return model;
	}

}
