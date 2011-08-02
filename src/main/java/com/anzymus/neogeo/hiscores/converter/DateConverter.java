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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
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

        if (durationInWeeks >= 1)
            return durationInWeeks + " week(s) ago";

        if (durationInDays >= 1)
            return durationInDays + " day(s) ago";

        if (durationInHours >= 1)
            return durationInHours + " hour(s) ago";

        if (durationInMinutes >= 1)
            return durationInMinutes + " minute(s) ago";

        return durationInSeconds + " second(s) ago";
    }

}
