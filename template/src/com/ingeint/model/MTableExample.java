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
 * Copyright (C) {year} INGEINT <http://www.ingeint.com>.
 * Copyright (C) Contributors.
 * 
 * Contributors:
 *    - {year} {name of contributor} <{email}>.
 */

package com.ingeint.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

/**
 * This is a example class for model. Name standard: M{table name without prefix}. Example name: X_IGI_TableExample -> MTableExample
 */
public class MTableExample extends X_IGI_TableExample implements DocAction, DocOptions {

	private static final long serialVersionUID = 113348969149628437L;

	public MTableExample(Properties ctx, int IGI_TableExample_ID, String trxName) {
		super(ctx, IGI_TableExample_ID, trxName);
	}

	public MTableExample(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	// DOC OPTIONS

	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index) {
		index = 0;

		if (docStatus.equals(STATUS_Drafted) || docStatus.equals(STATUS_InProgress) || docStatus.equals(STATUS_Invalid)) {
			options[index++] = ACTION_Complete;
		}

		return index;
	}

	// DOC ACTION

	private String processMsg;

	@Override
	public String getProcessMsg() {
		return processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	@Override
	public String getSummary() {
		StringBuilder summary = new StringBuilder();
		summary.append(getDocumentNo());
		// CUSTOM SUMMARY
		return summary.toString();
	}

	@Override
	public String getDocumentInfo() {
		MDocType docType = MDocType.get(getCtx(), getC_DocType_ID());
		StringBuilder documentInfo = new StringBuilder();
		documentInfo.append(docType.getNameTrl());
		documentInfo.append(" ");
		documentInfo.append(getDocumentNo());
		// CUSTOM DOCUMENT INFO
		return documentInfo.toString();
	}

	@Override
	public boolean processIt(String action) throws Exception {
		processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public String prepareIt() {
		return STATUS_InProgress;
	}

	@Override
	public String completeIt() {
		// MODEL VALIDATOR TIMING_BEFORE_COMPLETE
		String validBC = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (validBC != null) {
			processMsg = validBC;
			return DocAction.STATUS_Invalid;
		}

		// CUSTOM COMPLETE
		setProcessed(true);

		// MODEL VALIDATOR TIMING_AFTER_COMPLETE
		String validAC = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (validAC != null) {
			processMsg = validAC;
			return DocAction.STATUS_Invalid;
		}

		return STATUS_Completed;
	}

	@Override
	public boolean unlockIt() {
		return false;
	}

	@Override
	public boolean invalidateIt() {
		return false;
	}

	@Override
	public boolean approveIt() {
		return false;
	}

	@Override
	public boolean rejectIt() {
		return false;
	}

	@Override
	public boolean voidIt() {
		return false;
	}

	@Override
	public boolean closeIt() {
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		return false;
	}

	@Override
	public boolean reActivateIt() {
		return false;
	}

	@Override
	public File createPDF() {
		return null;
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return null;
	}

}
