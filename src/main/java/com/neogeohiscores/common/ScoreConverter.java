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

package com.neogeohiscores.common;

import com.neogeohiscores.entities.Score;

public class ScoreConverter {

    private String addDots(String score) {
        StringBuilder sb = new StringBuilder();
        int chara = 0;
        for (int i = score.length() - 1; i >= 0; i--) {
            if (chara % 3 == 0 && chara != 0) {
                sb.append(".");
            }
            sb.append(score.charAt(i));
            chara++;
        }
        score = sb.reverse().toString();
        return score;
    }

    public String getAsString(String scoreValue) {
        if (scoreValue == null) {
            return "";
        }
        Score score = new Score();
        score.setValue(scoreValue);

        if (!score.isChrono() && !score.isSoccer() && !score.isSoccerWithGoalAverage()) {
            scoreValue = addDots(scoreValue);
        }
        return scoreValue;
    }

}