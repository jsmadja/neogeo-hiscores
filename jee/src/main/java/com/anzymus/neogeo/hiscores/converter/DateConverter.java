/**
 *     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.anzymus.neogeo.hiscores.converter;

import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.ocpsoft.pretty.time.PrettyTime;

@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		PrettyTime prettyTime = new PrettyTime();
		if (context != null) {
			if (context.getExternalContext() != null) {
				Locale locale = context.getExternalContext().getRequestLocale();
				prettyTime = new PrettyTime(locale);
			}
		}
		Date date = (Date) value;
		return prettyTime.format(date);
	}

}
