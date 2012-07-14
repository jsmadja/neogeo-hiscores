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

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DateDDMMYYYYConverterTest {

    DateDDMMYYYYConverter converter = new DateDDMMYYYYConverter();

    @Test
    public void should_format_correctly() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 4);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.YEAR, 2011);

        Date date = cal.getTime();

        String strDate = converter.getAsString(null, null, date);

        Assert.assertEquals("4 Jul 2011", strDate);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throw_exception() {
        converter.getAsObject(null, null, null);
    }
}
