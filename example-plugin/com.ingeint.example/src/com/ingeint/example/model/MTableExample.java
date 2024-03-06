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
 * Copyright (c) 2016 Saúl Piña <sauljabin@gmail.com>.
 */

package com.ingeint.example.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Model example
 * https://wiki.idempiere.org/en/NF9_OSGi_New_Model_Factory
 */
public class MTableExample extends X_TL_TableExample {

	private static final long serialVersionUID = -8652573410419218188L;

	public MTableExample(Properties ctx, int TL_TableTemplate_ID, String trxName) {
		super(ctx, TL_TableTemplate_ID, trxName);
	}

	public MTableExample(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
