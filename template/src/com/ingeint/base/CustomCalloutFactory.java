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

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.util.CLogger;

/**
 * Custom Callout Factory
 */
public abstract class CustomCalloutFactory implements IColumnCalloutFactory {

	private final static CLogger log = CLogger.getCLogger(CustomCalloutFactory.class);
	private List<CalloutWrapper> cacheCallouts = new ArrayList<CalloutWrapper>();

	/**
	 * Inner class for callouts
	 */
	class CalloutWrapper {
		private String tableName;
		private String columnName;
		private Class<? extends CustomCallout> calloutClass;

		public String getTableName() {
			return tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public Class<? extends CustomCallout> getCalloutClass() {
			return calloutClass;
		}

		public CalloutWrapper(String tableName, String columnName, Class<? extends CustomCallout> calloutClass) {
			this.tableName = tableName;
			this.columnName = columnName;
			this.calloutClass = calloutClass;
		}

	}

	/**
	 * Register a new callout
	 * 
	 * @param tableName
	 *            Table for event
	 * @param columnName
	 *            Column for event
	 * @param callout
	 *            Event
	 */
	protected void registerCallout(String tableName, String columnName, Class<? extends CustomCallout> calloutClass) {
		cacheCallouts.add(new CalloutWrapper(tableName, columnName, calloutClass));
	}

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		List<IColumnCallout> callouts = new ArrayList<IColumnCallout>();

		for (int i = 0; i < cacheCallouts.size(); i++) {
			CalloutWrapper calloutWrapper = cacheCallouts.get(i);
			if (calloutWrapper.getTableName().equals(tableName) && calloutWrapper.getColumnName().equals(columnName)) {
				try {
					CustomCallout customCallout = calloutWrapper.getCalloutClass().newInstance();
					log.info(String.format("CalloutFactory [Table Name: %s, Column Name: %s, Callout: %s]", tableName, columnName, calloutWrapper.getCalloutClass().getName()));
					callouts.add(customCallout);
				} catch (Exception e) {
					log.severe(String.format("CalloutFactory [Table Name: %s, Column Name: %s, Callout: %s, Exception: %s]", tableName, columnName, calloutWrapper.getCalloutClass().getName(), e));
				}
			}
		}

		if (callouts.size() <= 0)
			return null;

		return callouts.toArray(new IColumnCallout[callouts.size()]);
	}

	/**
	 * Default constructor
	 */
	public CustomCalloutFactory() {
		initialize();
	}

	/**
	 * For initialize class. Register the custom callout to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerCallout(MTableExample.Table_Name, MTableExample.COLUMNNAME_Text, CPrintPluginInfo.class);
	 * }
	 * </pre>
	 */
	protected abstract void initialize();

}
