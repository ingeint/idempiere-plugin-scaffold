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

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.osgi.service.event.Event;

/**
 * Custom Event Factory
 */
public abstract class CustomEventFactory extends AbstractEventHandler implements IEventTopics {

	private final static CLogger log = CLogger.getCLogger(CustomEventFactory.class);
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
			if (eventHandlerWrapper.getFilter() != null) {
				if (isProcessHandler(eventType)) {
					ProcessInfo pi = getProcessInfo(event);
					if(pi.getAD_Process_UU().equals(eventHandlerWrapper.getFilter())
					|| pi.getClassName().equals(eventHandlerWrapper.getFilter())) {
						execEventHandler(event, eventHandlerWrapper, pi);
					}
				} else {
				PO po = getPO(event);
				String tableName = po.get_TableName();
					if (tableName.equals(eventHandlerWrapper.getFilter())) {
					execEventHandler(event, eventHandlerWrapper, po);
					}
				}
			} else {
				execEventHandler(event, eventHandlerWrapper, null);
			}
		}
	}

	private void execEventHandler(Event event, EventHandlerWrapper eventHandlerWrapper, Object data) {
		CustomEvent customEventHandler;
		try {
			customEventHandler = eventHandlerWrapper.getEventHandler().getConstructor().newInstance();
			log.info(String.format("CustomEvent created -> %s [Event Type: %s, PO: %s]", eventHandlerWrapper.toString(), event, data));
		} catch (Exception e) {
			throw new AdempiereException(e);
		}

		if(data instanceof PO)
			customEventHandler.doHandleEvent((PO)data, event);
		else if(data instanceof ProcessInfo)
			customEventHandler.doHandleEvent((ProcessInfo)data, event);
		else
			customEventHandler.doHandleEvent(event);
	}

	/**
	 * Register the table events
	 *
	 * @param eventTopic   Event type. Example: IEventTopics.DOC_AFTER_COMPLETE
	 * @param tableName    Table name
	 * @param eventHandler Event listeners
	 */
	protected void registerEvent(String eventTopic, String filter, Class<? extends CustomEvent> eventHandler) {
		boolean notRegistered = cacheEvents.stream().filter(event -> event.getEventTopic() == eventTopic).filter(event -> event.getFilter() == filter).findFirst().isEmpty();

		if (notRegistered) {
			if (filter == null) {
				registerEvent(eventTopic);
			} else if(isProcessHandler(eventTopic)) {
				registerProcessEvent(eventTopic, filter);
			} else {
				registerTableEvent(eventTopic, filter);
			}

		}

		cacheEvents.add(new EventHandlerWrapper(eventTopic, filter, eventHandler));
		log.info(String.format("CustomEvent registered -> %s [Topic: %s, Filter: %s]", eventHandler.getName(), eventTopic, filter));
	}

	/**
	 * Register event
	 *
	 * @param eventTopic   Event type. Example: IEventTopics.AFTER_LOGIN
	 * @param eventHandler Event listeners
	 */
	protected void registerEvent(String eventTopic, Class<? extends CustomEvent> eventHandler) {
		registerEvent(eventTopic, null, eventHandler);
	}

	private boolean isProcessHandler(String eventTopic) {
		return (IEventTopics.BEFORE_PROCESS.equals(eventTopic)
				|| IEventTopics.AFTER_PROCESS.equals(eventTopic)
				|| IEventTopics.POST_PROCESS.equals(eventTopic));
	}

	/**
	 * Inner class for event
	 */
	class EventHandlerWrapper {
		private String eventTopic;
		private String filter;
		private Class<? extends CustomEvent> eventHandler;

		public EventHandlerWrapper(String eventType, String filter, Class<? extends CustomEvent> eventHandlerClass) {
			this.eventTopic = eventType;
			this.filter = filter;
			this.eventHandler = eventHandlerClass;
		}

		public String getEventTopic() {
			return eventTopic;
		}

		public String getFilter() {
			return filter;
		}

		public Class<? extends CustomEvent> getEventHandler() {
			return eventHandler;
		}

		@Override
		public String toString() {
			return String.format("EventHandlerWrapper [eventTopic=%s, filter=%s, eventHandler=%s]", eventTopic, filter, eventHandler);
		}
	}

}
