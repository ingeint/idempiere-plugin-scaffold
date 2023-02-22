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
 * Copyright (C) ${year} ${plugin.vendor} and contributors (see README.md file).
 */

package ${plugin.root}.base;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Arrays;
import java.util.Optional;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import ${plugin.root}.annotation.ColumnCallout;
import ${plugin.root}.util.StringUtils;

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
	
	/**
	 * Set a new value to the current column
	 * 
	 * @param newValue New value
	 * @return Error message or empty
	 */
	public String setValue(Object newValue) {
		return mTab.setValue(getColumnName(), newValue);
	}
	
	/**
	 * Set a new value to the selected column
	 * 
	 * @param columnName Column to change
	 * @param newValue   New value
	 * @return Error message or empty
	 */
	public String setValue(String columnName, Object newValue) {
		return mTab.setValue(columnName, newValue);
	}
	
	/**
	 * 
	 * @return the int value
	 */
	public int getValueAsInt() {
		return Optional.ofNullable((Integer) value)
				.orElse(0);
	}
	
	/**
	 * 
	 * @return the old value as int
	 */
	public int getOldValueAsInt() {
		return Optional.ofNullable((Integer) oldValue)
				.orElse(0);
	}
	
	/**
	 * 
	 * @param columnName Column to get as int
	 * @return the int value
	 */
	public int getValueAsInt(String columnName) {
		return Optional.ofNullable((Integer) getValue(columnName))
				.orElse(0);
	}
	
	/**
	 * 
	 * @param columnName Column to get
	 * @return the value
	 */
	public Object getValue(String columnName) {
		return getTab().getValue(columnName);
	}
	
	/**
	 * 
	 * @return the value as boolean
	 */
	public boolean getValueAsBoolean() {
		if (value instanceof String)
			return "Y".equals(value);
		return Optional.ofNullable((Boolean) value)
				.orElse(false);
	}
	
	/**
	 * 
	 * @return the old value as boolean
	 */
	public boolean getOldValueAsBoolean() {
		if (oldValue instanceof String)
			return "Y".equals(oldValue);
		return Optional.ofNullable((Boolean) oldValue)
				.orElse(false);
	}
	
	/**
	 * 
	 * @param columnName Column to get
	 * @return the value as boolean
	 */
	public boolean getValueAsBoolean(String columnName) {
		return getTab().getValueAsBoolean(columnName);
	}
	
	/**
	 * 
	 * @param columnName Column to get
	 * @return the value as string
	 */
	public String getValueAsString(String columnName) {
		return Optional.ofNullable((String) getValue(columnName))
				.orElse("");
	}
	
	/**
	 * 
	 * @return the value as string
	 */
	public String getValueAsString() {
		return Optional.ofNullable((String) value)
				.orElse("");
	}
	
	/**
	 * 
	 * @return the old value as string
	 */
	public String getOldValueAsString() {
		return Optional.ofNullable((String) oldValue)
				.orElse("");
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
	protected String start() {
		String columnName = getColumnName();
		
		Method[] methods = Arrays.stream(getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(ColumnCallout.class)
						&& StringUtils.isValuePresent(method.getAnnotation(ColumnCallout.class).columnNames(), columnName)
						&& method.getReturnType().equals(String.class))
				.sorted(CustomCallout::sort)
				.toArray(Method[]::new);
		
		for (Method method: methods)
		{
			String error = execute(method);
			
			if (!Util.isEmpty(error, true))
				return error;
		}
		
		return "";
	}
	
	private static int sort(Method method1, Method method2) {
		ColumnCallout columnCallout1 = method1.getAnnotation(ColumnCallout.class);
		ColumnCallout columnCallout2 = method2.getAnnotation(ColumnCallout.class);
		
		return columnCallout1.order() > columnCallout2.order() ? 1
				: (columnCallout1.order() == columnCallout2.order() ? 0 : -1);
	}
	
	private String execute(Method method) {
		try {
			return (String) method.invoke(this);
		} catch (Exception e) {
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
	}
}
