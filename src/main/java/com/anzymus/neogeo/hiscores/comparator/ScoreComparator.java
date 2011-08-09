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

package com.anzymus.neogeo.hiscores.comparator;

import java.util.Calendar;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreComparator {

    public static Score max(Score score1, Score score2) {
        try {
            return maxAsInt(score1, score2);
        } catch (Throwable t) {
            return score1;
        }
    }

    private static Score maxAsInt(Score score1, Score score2) {
        Integer score1asInt = Integer.parseInt(score1.getValue());
        Integer score2asInt = Integer.parseInt(score2.getValue());
        return score1asInt > score2asInt ? score1 : score2;
    }

    public static String gap(String score1, String score2) {
        try {
            if (areChrono(score1, score2)) {
                return gapAsChrono(score1, score2);
            }
            return gapAsInt(score1, score2);
        } catch (Throwable t) {
            return "";
        }
    }

    private static boolean areChrono(String score1, String score2) {
        return score1.contains(":") && score2.contains(":");
    }

    private static String gapAsChrono(String score1, String score2) {
        long time1 = toMilliseconds(score1);
        long time2 = toMilliseconds(score2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Math.abs(time2 - time1));
        String minutes = Long.toString(calendar.get(Calendar.MINUTE));
        String seconds = Long.toString(calendar.get(Calendar.SECOND));
        String milliseconds = Long.toString(calendar.get(Calendar.MILLISECOND) / 10);
        seconds = addZero(seconds);
        milliseconds = addZero(milliseconds);
        return minutes + ":" + seconds + ":" + milliseconds;
    }

    private static String addZero(String value) {
        if (value.length() == 1) {
            return addZero("0" + value);
        }
        return value;
    }

    private static long toMilliseconds(String score) {
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

    private static String gapAsInt(String score1, String score2) {
        Integer score1asInt = Integer.parseInt(score1);
        Integer score2asInt = Integer.parseInt(score2);
        return Integer.toString(Math.abs(score1asInt - score2asInt));
    }

}
