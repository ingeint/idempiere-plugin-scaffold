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
 * Copyright (C) 2024 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.example.base;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * Custom Process
 */
public abstract class CustomProcess extends SvrProcess {

	@Override
	protected void prepare() {
		// empty on purpose
	}

	/**
	 * Get parameter
	 * 
	 * @param parameterName Parameter name to find
	 * @return null if no exist
	 */
	protected Object getParameter(String parameterName) {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (name != null)
				if (name.equals(parameterName))
					return para[i].getParameter();
		}
		return null;
	}

}
