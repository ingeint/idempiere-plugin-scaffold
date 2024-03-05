/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.ingeint.example.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/**
 * Generated Model for TL_TableTemplate
 * 
 * @author iDempiere (generated)
 * @version Release 3.1 - $Id$
 */
public class X_TL_TableTemplate extends PO implements I_TL_TableTemplate, I_Persistent {

	/**
	 *
	 */
	private static final long serialVersionUID = 20160309L;

	/** Standard Constructor */
	public X_TL_TableTemplate(Properties ctx, int TL_TableTemplate_ID, String trxName) {
		super(ctx, TL_TableTemplate_ID, trxName);
		/**
		 * if (TL_TableTemplate_ID == 0) { setTL_TableTemplate_ID (0);
		 * setTL_TableTemplate_UU (null); }
		 */
	}

	/** Load Constructor */
	public X_TL_TableTemplate(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * AccessLevel
	 * 
	 * @return 3 - Client - Org
	 */
	protected int get_AccessLevel() {
		return accessLevel.intValue();
	}

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("X_TL_TableTemplate[").append(get_ID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Description.
	 * 
	 * @param Description Optional short description of the record
	 */
	public void setDescription(String Description) {
		set_Value(COLUMNNAME_Description, Description);
	}

	/**
	 * Get Description.
	 * 
	 * @return Optional short description of the record
	 */
	public String getDescription() {
		return (String) get_Value(COLUMNNAME_Description);
	}

	/**
	 * Set Table Template.
	 * 
	 * @param TL_TableTemplate_ID Table Template
	 */
	public void setTL_TableTemplate_ID(int TL_TableTemplate_ID) {
		if (TL_TableTemplate_ID < 1)
			set_ValueNoCheck(COLUMNNAME_TL_TableTemplate_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_TL_TableTemplate_ID, Integer.valueOf(TL_TableTemplate_ID));
	}

	/**
	 * Get Table Template.
	 * 
	 * @return Table Template
	 */
	public int getTL_TableTemplate_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_TL_TableTemplate_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set TL_TableTemplate_UU.
	 * 
	 * @param TL_TableTemplate_UU TL_TableTemplate_UU
	 */
	public void setTL_TableTemplate_UU(String TL_TableTemplate_UU) {
		set_ValueNoCheck(COLUMNNAME_TL_TableTemplate_UU, TL_TableTemplate_UU);
	}

	/**
	 * Get TL_TableTemplate_UU.
	 * 
	 * @return TL_TableTemplate_UU
	 */
	public String getTL_TableTemplate_UU() {
		return (String) get_Value(COLUMNNAME_TL_TableTemplate_UU);
	}
}