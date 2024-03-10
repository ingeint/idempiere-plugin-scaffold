package com.ingeint.example.event;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.event.LoginEventData;
import org.adempiere.base.event.annotations.AfterLoginEventDelegate;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.osgi.service.event.Event;

/**
 * Event example
 * https://wiki.idempiere.org/en/NF9_OSGi_New_Event_Handling_Annotation
 */
@EventTopicDelegate
public class ELoginInfo extends AfterLoginEventDelegate {

	private final static CLogger log = CLogger.getCLogger(ELoginInfo.class);

	public ELoginInfo(Event event) {
		super(event);
	}

	@Override
	public void onAfterLogin(LoginEventData data) {
		MUser loginUser = MUser.get(data.getAD_User_ID());
		log.info("User: " + loginUser.getName());
	}
}
