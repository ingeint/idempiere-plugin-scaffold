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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for TL_TableDocExample
 *  @author iDempiere (generated)
 *  @version Release 11 - $Id$ */
@org.adempiere.base.Model(table="TL_TableDocExample")
public class X_TL_TableDocExample extends PO implements I_TL_TableDocExample, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240305L;

    /** Standard Constructor */
    public X_TL_TableDocExample (Properties ctx, int TL_TableDocExample_ID, String trxName)
    {
      super (ctx, TL_TableDocExample_ID, trxName);
      /** if (TL_TableDocExample_ID == 0)
        {
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
// N
			setTL_TableDocExample_ID (0);
			setTL_TableDocExample_UU (null);
        } */
    }

    /** Standard Constructor */
    public X_TL_TableDocExample (Properties ctx, int TL_TableDocExample_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, TL_TableDocExample_ID, trxName, virtualColumns);
      /** if (TL_TableDocExample_ID == 0)
        {
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
// N
			setTL_TableDocExample_ID (0);
			setTL_TableDocExample_UU (null);
        } */
    }

    /** Standard Constructor */
    public X_TL_TableDocExample (Properties ctx, String TL_TableDocExample_UU, String trxName)
    {
      super (ctx, TL_TableDocExample_UU, trxName);
      /** if (TL_TableDocExample_UU == null)
        {
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
// N
			setTL_TableDocExample_ID (0);
			setTL_TableDocExample_UU (null);
        } */
    }

    /** Standard Constructor */
    public X_TL_TableDocExample (Properties ctx, String TL_TableDocExample_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, TL_TableDocExample_UU, trxName, virtualColumns);
      /** if (TL_TableDocExample_UU == null)
        {
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
// N
			setTL_TableDocExample_ID (0);
			setTL_TableDocExample_UU (null);
        } */
    }

    /** Load Constructor */
    public X_TL_TableDocExample (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_TL_TableDocExample[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_ID)
			.getPO(getC_DocType_ID(), get_TrxName());
	}

	/** Set Document Type.
		@param C_DocType_ID Document type or rules
	*/
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc Date of the Document
	*/
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** &lt;None&gt; = -- */
	public static final String DOCACTION_None = "--";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Set Document Action.
		@param DocAction The targeted status of the document
	*/
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction()
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Set Document Status.
		@param DocStatus The current status of the document
	*/
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus()
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo Document sequence number of the document
	*/
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo()
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Table Doc Example.
		@param TL_TableDocExample_ID Table Doc Example
	*/
	public void setTL_TableDocExample_ID (int TL_TableDocExample_ID)
	{
		if (TL_TableDocExample_ID < 1)
			set_ValueNoCheck (COLUMNNAME_TL_TableDocExample_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_TL_TableDocExample_ID, Integer.valueOf(TL_TableDocExample_ID));
	}

	/** Get Table Doc Example.
		@return Table Doc Example	  */
	public int getTL_TableDocExample_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TL_TableDocExample_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TL_TableDocExample_UU.
		@param TL_TableDocExample_UU TL_TableDocExample_UU
	*/
	public void setTL_TableDocExample_UU (String TL_TableDocExample_UU)
	{
		set_ValueNoCheck (COLUMNNAME_TL_TableDocExample_UU, TL_TableDocExample_UU);
	}

	/** Get TL_TableDocExample_UU.
		@return TL_TableDocExample_UU	  */
	public String getTL_TableDocExample_UU()
	{
		return (String)get_Value(COLUMNNAME_TL_TableDocExample_UU);
	}
}