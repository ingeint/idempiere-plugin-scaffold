package com.ingeint.template.event;

import java.util.Properties;

import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import com.ingeint.template.base.CustomEvent;

public class ELogLoginInfo extends CustomEvent {

	private final static CLogger log = CLogger.getCLogger(ELogLoginInfo.class);

	@Override
	protected void doHandleEvent() {
		Properties ctx = Env.getCtx();
		int userId = Env.getAD_User_ID(ctx);

		MUser loginUser = MUser.get(ctx, userId);

		log.info("User: " + loginUser.getName());
	}

}
