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
 * Copyright (C) 2015 INGEINT <http://www.ingeint.com> and contributors (see README.md file).
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
			execHandler(event, eventType, eventHandlerWrapper);
		}
	}

	private void execHandler(Event event, String eventType, EventHandlerWrapper eventHandlerWrapper) {
		if (eventType.equals(eventHandlerWrapper.getEventTopic())) {
			if (eventHandlerWrapper.getTableName() != null) {
				PO po = getPO(event);
				String tableName = po.get_TableName();
				if (tableName.equals(eventHandlerWrapper.getTableName())) {
					execEventHandler(event, eventHandlerWrapper, po);
				}
			} else {
				execEventHandler(event, eventHandlerWrapper, null);
			}
		}
	}

	private void execEventHandler(Event event, EventHandlerWrapper eventHandlerWrapper, PO po) {
		log.info(String.format("EventManager [Event Type: %s, Custom Event: %s]", event, eventHandlerWrapper.toString()));
		CustomEventHandler customEventHandler;
		try {
			customEventHandler = eventHandlerWrapper.getEventHandler().getConstructor().newInstance();
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		customEventHandler.doHandleEvent(po, event);
	}

	/**
	 * Register the table events
	 *
	 * @param eventTopic   Event type. Example: IEventTopics.DOC_AFTER_COMPLETE
	 * @param tableName    Table name
	 * @param eventHandler Event listeners
	 */
	protected void registerEvent(String eventTopic, String tableName, Class<? extends CustomEventHandler> eventHandler) {
		boolean notRegistered = cacheEvents.stream().filter(event -> event.getEventTopic() == eventTopic).filter(event -> event.getTableName() == tableName).findFirst().isEmpty();

		if (notRegistered) {
			if (tableName == null) {
				registerEvent(eventTopic);
			} else {
				registerTableEvent(eventTopic, tableName);
			}
		}

		cacheEvents.add(new EventHandlerWrapper(eventTopic, tableName, eventHandler));
		log.info(String.format("Register Event -> EventManager [Table Name: %s, Topic: %s, Event: %s]", tableName, eventTopic, eventHandler.getName()));
	}

	/**
	 * Register event
	 *
	 * @param eventTopic   Event type. Example: IEventTopics.AFTER_LOGIN
	 * @param eventHandler Event listeners
	 */
	protected void registerEvent(String eventTopic, Class<? extends CustomEventHandler> eventHandler) {
		registerEvent(eventTopic, null, eventHandler);
	}

	/**
	 * Inner class for event
	 */
	class EventHandlerWrapper {
		private String eventTopic;
		private String tableName;
		private Class<? extends CustomEventHandler> eventHandler;

		public EventHandlerWrapper(String eventType, String tableName, Class<? extends CustomEventHandler> eventHandlerClass) {
			this.eventTopic = eventType;
			this.tableName = tableName;
			this.eventHandler = eventHandlerClass;
		}

		public String getEventTopic() {
			return eventTopic;
		}

		public String getTableName() {
			return tableName;
		}

		public Class<? extends CustomEventHandler> getEventHandler() {
			return eventHandler;
		}

		@Override
		public String toString() {
			return String.format("EventHandlerWrapper [eventTopic=%s, tableName=%s, eventHandler=%s]", eventTopic, tableName, eventHandler);
		}
	}

}
