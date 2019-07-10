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
 * Copyright (C) ${year} INGEINT <http://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.base;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.osgi.service.event.Event;

/**
 * Custom Event Manager
 */
public abstract class CustomEventManager extends AbstractEventHandler implements IEventTopics {

	private final static CLogger log = CLogger.getCLogger(CustomEventManager.class);
	private List<EventHandlerWrapper> cacheEvents = new ArrayList<EventHandlerWrapper>();

	@Override
	protected void doHandleEvent(Event event) {
		String eventType = event.getTopic();

		for (int i = 0; i < cacheEvents.size(); i++) {
			EventHandlerWrapper eventHandlerWrapper = cacheEvents.get(i);

			if (eventType.equals(eventHandlerWrapper.getEventType())) {
				if (eventHandlerWrapper.getTableName() != null) {
					PO po = getPO(event);
					String tableName = po.get_TableName();
					if (tableName.equals(eventHandlerWrapper.getTableName())) {
						log.info(String.format("EventManager [Event Type: %s, Table Name: %s, Custom Event: %s]", eventType, tableName, eventHandlerWrapper.getEventHandlerClass().getName()));
						CustomEventHandler customEventHandler;
						try {
							customEventHandler = eventHandlerWrapper.getEventHandlerClass().getConstructor().newInstance();
						} catch (Exception e) {
							throw new AdempiereException(String.format("EventManager [Event Type: %s, Class %s can not be instantiated]", eventType, eventHandlerWrapper.getEventHandlerClass().getName()), e);
						}
						customEventHandler.doHandleEvent(po, event);
						break;
					}
				} else {
					log.info(String.format("EventManager [Event Type: %s, Custom Event: %s]", eventType, eventHandlerWrapper.getEventHandlerClass().getName()));
					CustomEventHandler customEventHandler;
					try {
						customEventHandler = eventHandlerWrapper.getEventHandlerClass().getConstructor().newInstance();
					} catch (Exception e) {
						throw new AdempiereException(String.format("EventManager [Event Type: %s, Class %s can not be instantiated]", eventType, eventHandlerWrapper.getEventHandlerClass().getName()), e);
					}
					customEventHandler.doHandleEvent(null, event);
					break;
				}
			}
		}
	}

	/**
	 * Register the table events
	 *
	 * @param topic     Event type. Example: IEventTopics.DOC_AFTER_COMPLETE
	 * @param tableName Table name
	 * @param event     Event listener
	 */
	protected void registerTableEvent(String topic, String tableName, Class<? extends CustomEventHandler> eventHandlerClass) {
		cacheEvents.add(new EventHandlerWrapper(topic, tableName, eventHandlerClass));
		registerTableEvent(topic, tableName);
		log.info(String.format("Register TableEvent -> EventManager [Table Name: %s, Topic: %s, Event: %s]", tableName, topic, eventHandlerClass.getName()));
	}

	/**
	 * Register event
	 *
	 * @param topic Event type. Example: IEventTopics.AFTER_LOGIN
	 * @param event Event listener
	 */
	protected void registerEvent(String topic, Class<? extends CustomEventHandler> eventHandlerClass) {
		cacheEvents.add(new EventHandlerWrapper(topic, null, eventHandlerClass));
		registerEvent(topic);
		log.info(String.format("Register Event -> EventManager [Topic: %s, Event: %s]", topic, eventHandlerClass.getName()));
	}

	/**
	 * Inner class for event
	 */
	class EventHandlerWrapper {
		private String eventType;
		private String tableName;
		private Class<? extends CustomEventHandler> eventHandlerClass;

		public EventHandlerWrapper(String eventType, String tableName, Class<? extends CustomEventHandler> eventHandlerClass) {
			this.eventType = eventType;
			this.tableName = tableName;
			this.eventHandlerClass = eventHandlerClass;
		}

		public String getEventType() {
			return eventType;
		}

		public String getTableName() {
			return tableName;
		}

		public Class<? extends CustomEventHandler> getEventHandlerClass() {
			return eventHandlerClass;
		}

	}

}
