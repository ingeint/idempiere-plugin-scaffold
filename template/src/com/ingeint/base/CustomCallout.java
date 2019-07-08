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

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

/**
 * Custom IColumnCallout
 */
public abstract class CustomCallout implements IColumnCallout {

	private Properties ctx;
	private int WindowNo;
	private GridTab mTab;
	private GridField mField;
	private Object value;
	private Object oldValue;

	/**
	 * Gets the context
	 * 
	 * @return the ctx
	 */
	public Properties getCtx() {
		return ctx;
	}

	/**
	 * Gets the Window No
	 * 
	 * @return The Window No
	 */
	public int getWindowNo() {
		return WindowNo;
	}

	/**
	 * Gets the Tab
	 * 
	 * @return The Tab
	 */
	public GridTab getTab() {
		return mTab;
	}

	/**
	 * Gets the field
	 * 
	 * @return The Field
	 */
	public GridField getField() {
		return mField;
	}

	/**
	 * Gets de current value of field
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the old value of field
	 * 
	 * @return The Old Value
	 */
	public Object getOldValue() {
		return oldValue;
	}

	/**
	 * Gets the table name
	 * 
	 * @return Table name of event
	 */
	public String getTableName() {
		return mTab.getTableName();
	}

	/**
	 * Gets the column name
	 * 
	 * @return Column name of event
	 */
	public String getColumnName() {
		return mField.getColumnName();
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		this.ctx = ctx;
		this.WindowNo = WindowNo;
		this.mTab = mTab;
		this.mField = mField;
		this.value = value;
		this.oldValue = oldValue;
		return start();
	}

	/**
	 * Custom event execution
	 * 
	 * @return null if no error
	 */
	protected abstract String start();

}
