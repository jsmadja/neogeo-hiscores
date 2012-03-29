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

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class DateConverterTest {

	DateConverter dateConverter = new DateConverter();
	static FacesContext frenchContext;

	@BeforeClass
	public static void init() {
		frenchContext = Mockito.mock(FacesContext.class);
		ExternalContext externalContext = Mockito.mock(ExternalContext.class);
		Mockito.when(externalContext.getRequestLocale()).thenReturn(Locale.FRENCH);
		Mockito.when(frenchContext.getExternalContext()).thenReturn(externalContext);
	}

	@Test
	public void should_format_in_weeks() {
		DateTime cal = new DateTime();
		cal = cal.minusWeeks(2);
		assertEquals("1 week ago", dateConverter.getAsString(null, null, cal.toDate()));
	}

	@Test
	public void should_format_in_weeks_in_french() {
		DateTime cal = new DateTime();
		cal = cal.minusWeeks(2);
		assertEquals("il y a 1 semaine", dateConverter.getAsString(frenchContext, null, cal.toDate()));
	}

	@Test
	public void should_format_in_days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		assertEquals("1 day ago", dateConverter.getAsString(null, null, cal.getTime()));

		cal.add(Calendar.DAY_OF_YEAR, -2);
		assertEquals("3 days ago", dateConverter.getAsString(null, null, cal.getTime()));
	}

	@Test
	public void should_format_in_days_in_french() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		assertEquals("il y a 1 jour", dateConverter.getAsString(frenchContext, null, cal.getTime()));

		cal.add(Calendar.DAY_OF_YEAR, -2);
		assertEquals("il y a 3 jours", dateConverter.getAsString(frenchContext, null, cal.getTime()));
	}

	@Test
	public void should_format_in_hours() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		assertEquals("1 hour ago", dateConverter.getAsString(null, null, cal.getTime()));

		cal.add(Calendar.HOUR_OF_DAY, -2);
		assertEquals("3 hours ago", dateConverter.getAsString(null, null, cal.getTime()));
	}

	@Test
	public void should_format_in_hours_in_french() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		assertEquals("il y a 1 heure", dateConverter.getAsString(frenchContext, null, cal.getTime()));

		cal.add(Calendar.HOUR_OF_DAY, -2);
		assertEquals("il y a 3 heures", dateConverter.getAsString(frenchContext, null, cal.getTime()));
	}

	@Test
	public void should_format_in_minutes() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1);
		assertEquals("1 minute ago", dateConverter.getAsString(null, null, cal.getTime()));

		cal.add(Calendar.MINUTE, -2);
		assertEquals("3 minutes ago", dateConverter.getAsString(null, null, cal.getTime()));
	}

	@Test
	public void should_format_in_minutes_in_french() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1);
		assertEquals("il y a 1 minute", dateConverter.getAsString(frenchContext, null, cal.getTime()));

		cal.add(Calendar.MINUTE, -2);
		assertEquals("il y a 3 minutes", dateConverter.getAsString(frenchContext, null, cal.getTime()));
	}

	@Test
	public void should_format_in_seconds() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -1);
		assertEquals("1 second ago", dateConverter.getAsString(null, null, cal.getTime()));

		cal.add(Calendar.SECOND, -2);
		assertEquals("3 seconds ago", dateConverter.getAsString(null, null, cal.getTime()));
	}

	@Test
	public void should_format_in_seconds_in_french() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -1);
		assertEquals("il y a 1 seconde", dateConverter.getAsString(frenchContext, null, cal.getTime()));

		cal.add(Calendar.SECOND, -2);
		assertEquals("il y a 3 secondes", dateConverter.getAsString(frenchContext, null, cal.getTime()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void should_throw_exception() {
		dateConverter.getAsObject(null, null, null);
	}
}
