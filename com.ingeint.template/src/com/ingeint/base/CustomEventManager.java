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

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.osgi.service.event.Event;

/**
 * Custom Event Manager
 */
public abstract class CustomEventManager extends AbstractEventHandler {

	private final static CLogger log = CLogger.getCLogger(CustomEventManager.class);
	private List<EventHandlerWrapper> cacheEvents = new ArrayList<EventHandlerWrapper>();

	/**
	 * Inner class for event
	 */
	class EventHandlerWrapper {
		private String eventType;
		private String tableName;
		private Class<? extends CustomEventHandler> eventHandlerClass;

		public String getEventType() {
			return eventType;
		}

		public String getTableName() {
			return tableName;
		}

		public Class<? extends CustomEventHandler> getEventHandlerClass() {
			return eventHandlerClass;
		}

		public EventHandlerWrapper(String eventType, String tableName, Class<? extends CustomEventHandler> eventHandlerClass) {
			this.eventType = eventType;
			this.tableName = tableName;
			this.eventHandlerClass = eventHandlerClass;
		}

	}

	@Override
	protected void doHandleEvent(Event event) {
		String eventType = event.getTopic();

		for (int i = 0; i < cacheEvents.size(); i++) {
			EventHandlerWrapper eventHandlerWrapper = cacheEvents.get(i);

			if (eventHandlerWrapper.getTableName() != null) {
				PO po = getPO(event);
				String tableName = po.get_TableName();
				if (tableName.equals(eventHandlerWrapper.getTableName()) && eventType.equals(eventHandlerWrapper.getEventType())) {
					try {
						CustomEventHandler customEventHandler = eventHandlerWrapper.getEventHandlerClass().newInstance();
						log.info(String.format("EventManager [Event Type: %s, Table Name: %s, Custom Event: %s]", eventType, tableName, eventHandlerWrapper.getEventHandlerClass().getName()));
						customEventHandler.doHandleEvent(po, event);
						break;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new AdempiereException(String.format("EventManager [Event Type: %s, Class %s can not be instantiated for table: %s]", eventType, eventHandlerWrapper.getEventHandlerClass().getName(), tableName), e);
					}
				}
			} else {
				if (eventType.equals(eventHandlerWrapper.getEventType())) {
					try {
						CustomEventHandler customEventHandler = eventHandlerWrapper.getEventHandlerClass().newInstance();
						log.info(String.format("EventManager [Event Type: %s, Custom Event: %s]", eventType, eventHandlerWrapper.getEventHandlerClass().getName()));
						customEventHandler.doHandleEvent(null, event);
						break;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new AdempiereException(String.format("EventManager [Event Type: %s, Class %s can not be instantiated]", eventType, eventHandlerWrapper.getEventHandlerClass().getName()), e);
					}
				}
			}
		}
	}

	/**
	 * Register the table events
	 * 
	 * @param topic
	 *            Event type. Example: IEventTopics.DOC_AFTER_COMPLETE
	 * @param tableName
	 *            Table name
	 * @param event
	 *            Event listener
	 */
	protected void registerTableEvent(String topic, String tableName, Class<? extends CustomEventHandler> eventHandlerClass) {
		cacheEvents.add(new EventHandlerWrapper(topic, tableName, eventHandlerClass));
		registerTableEvent(topic, tableName);
	}

	/**
	 * Register event
	 * 
	 * @param topic
	 *            Event type. Example: IEventTopics.AFTER_LOGIN
	 * @param event
	 *            Event listener
	 */
	protected void registerEvent(String topic, Class<? extends CustomEventHandler> eventHandlerClass) {
		cacheEvents.add(new EventHandlerWrapper(topic, null, eventHandlerClass));
		registerEvent(topic);
	}

}
