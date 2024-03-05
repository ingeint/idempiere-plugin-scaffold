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

import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 * Custom class for forms
 */
public abstract class CustomForm extends ADForm implements IFormController, EventListener<Event> {

	private static final long serialVersionUID = 1393259812994414770L;

	@Override
	public ADForm getForm() {
		return this;
	}

}
