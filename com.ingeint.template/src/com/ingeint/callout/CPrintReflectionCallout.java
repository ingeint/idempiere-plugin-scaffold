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

package com.ingeint.callout;

import java.util.Arrays;
import java.util.Set;

import org.compiere.util.CLogger;
import org.reflections.Reflections;

import com.ingeint.base.Callout;
import com.ingeint.base.CustomCallout;
import com.ingeint.model.MTableTemplate;

@Callout(tableName = MTableTemplate.Table_Name, columnName = MTableTemplate.COLUMNNAME_Description)
public class CPrintReflectionCallout extends CustomCallout {

	private final static CLogger log = CLogger.getCLogger(CPrintReflectionCallout.class);

	@Override
	protected String start() {
		Reflections reflections = new Reflections("");
		Set<Class<?>> callouts = reflections.getTypesAnnotatedWith(Callout.class);
		Set<Class<? extends CustomCallout>> callouts2 = reflections.getSubTypesOf(CustomCallout.class);

		callouts.forEach(callout -> {
			System.out.println(Arrays.toString(callout.getAnnotation(Callout.class).tableName()));
		});

		log.info(callouts.toString());
		return null;
	}
}
