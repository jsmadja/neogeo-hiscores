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

package com.neogeohiscores.entities;

import java.util.Calendar;

public class ChronoScore {

    private long time;

    public ChronoScore(Score score) {
        time = toMilliseconds(score.getValue());
    }

    public String gap(ChronoScore score) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Math.abs(score.time - time));
        String minutes = Long.toString(calendar.get(Calendar.MINUTE));
        String seconds = Long.toString(calendar.get(Calendar.SECOND));
        String milliseconds = Long.toString(calendar.get(Calendar.MILLISECOND) / 10);
        seconds = addZero(seconds);
        milliseconds = addZero(milliseconds);
        return minutes + ":" + seconds + ":" + milliseconds;
    }

    private String addZero(String value) {
        if (value.length() == 1) {
            return addZero("0" + value);
        }
        return value;
    }

    private long toMilliseconds(String score) {
        Calendar calendar = Calendar.getInstance();
        String[] data = score.split(":");
        int minutes = Integer.parseInt(data[0]);
        int seconds = Integer.parseInt(data[1]);
        int milliseconds = Integer.parseInt(data[2]) * 10;
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        long time1 = calendar.getTimeInMillis();
        return time1;
    }

}
