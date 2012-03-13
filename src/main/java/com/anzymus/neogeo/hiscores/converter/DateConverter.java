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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

	private static final Logger LOG = LoggerFactory.getLogger(DateConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (context != null) {
			if (context.getExternalContext() != null) {
				Locale locale = context.getExternalContext().getRequestLocale();
				if (Locale.FRENCH.equals(locale)) {
					return toFormattedDateInFrench((Date) value);
				} else {
					LOG.info("Locale: " + locale + " is not handled");
				}
			}
		}
		Date date = (Date) value;
		return toFormattedDate(date);
	}

	String toFormattedDate(Date date) {
		long current = System.currentTimeMillis();
		long event = date.getTime();

		long durationInSeconds = (current - event) / 1000;
		long durationInMinutes = durationInSeconds / 60;
		long durationInHours = durationInMinutes / 60;
		long durationInDays = durationInHours / 24;
		long durationInWeeks = durationInDays / 7;

		String formattedDate = "";
		long finalDuration = durationInSeconds;
		if (durationInWeeks >= 1) {
			formattedDate += durationInWeeks + " week";
			finalDuration = durationInWeeks;
		} else if (durationInDays >= 1) {
			formattedDate += durationInDays + " day";
			finalDuration = durationInDays;
		} else if (durationInHours >= 1) {
			formattedDate += durationInHours + " hour";
			finalDuration = durationInHours;
		} else if (durationInMinutes >= 1) {
			formattedDate += durationInMinutes + " minute";
			finalDuration = durationInMinutes;
		} else
			formattedDate += durationInSeconds + " second";
		if (finalDuration > 1) {
			formattedDate += "s";
		}
		return formattedDate + " ago";
	}

	String toFormattedDateInFrench(Date date) {
		long current = System.currentTimeMillis();
		long event = date.getTime();

		long durationInSeconds = (current - event) / 1000;
		long durationInMinutes = durationInSeconds / 60;
		long durationInHours = durationInMinutes / 60;
		long durationInDays = durationInHours / 24;
		long durationInWeeks = durationInDays / 7;

		String formattedDate = "il y a ";
		long finalDuration = durationInSeconds;
		if (durationInWeeks >= 1) {
			formattedDate += durationInWeeks + " semaine";
			finalDuration = durationInWeeks;
		} else if (durationInDays >= 1) {
			formattedDate += durationInDays + " jour";
			finalDuration = durationInDays;
		} else if (durationInHours >= 1) {
			formattedDate += durationInHours + " heure";
			finalDuration = durationInHours;
		} else if (durationInMinutes >= 1) {
			formattedDate += durationInMinutes + " minute";
			finalDuration = durationInMinutes;
		} else
			formattedDate += durationInSeconds + " seconde";
		if (finalDuration > 1) {
			formattedDate += "s";
		}
		return formattedDate;
	}
}
