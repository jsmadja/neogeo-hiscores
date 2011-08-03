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
import org.junit.Test;

public class DateConverterTest {

    DateConverter dateConverter = new DateConverter();

    @Test
    public void should_format_in_weeks() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        assertEquals("1 week(s) ago", dateConverter.getAsString(null, null, cal.getTime()));

        cal.add(Calendar.WEEK_OF_YEAR, -2);
        assertEquals("3 week(s) ago", dateConverter.getAsString(null, null, cal.getTime()));
    }

    @Test
    public void should_format_in_days() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        assertEquals("1 day(s) ago", dateConverter.getAsString(null, null, cal.getTime()));

        cal.add(Calendar.DAY_OF_YEAR, -2);
        assertEquals("3 day(s) ago", dateConverter.getAsString(null, null, cal.getTime()));
    }

    @Test
    public void should_format_in_hours() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        assertEquals("1 hour(s) ago", dateConverter.getAsString(null, null, cal.getTime()));

        cal.add(Calendar.HOUR_OF_DAY, -2);
        assertEquals("3 hour(s) ago", dateConverter.getAsString(null, null, cal.getTime()));
    }

    @Test
    public void should_format_in_minutes() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        assertEquals("1 minute(s) ago", dateConverter.getAsString(null, null, cal.getTime()));

        cal.add(Calendar.MINUTE, -2);
        assertEquals("3 minute(s) ago", dateConverter.getAsString(null, null, cal.getTime()));
    }

    @Test
    public void should_format_in_seconds() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -1);
        assertEquals("1 second(s) ago", dateConverter.getAsString(null, null, cal.getTime()));

        cal.add(Calendar.SECOND, -2);
        assertEquals("3 second(s) ago", dateConverter.getAsString(null, null, cal.getTime()));
    }
}
