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

import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreComparator {

    public static Score max(Score score1, Score score2) {
        return maxAsInt(score1, score2);
    }
    
    private static Score maxAsInt(Score score1, Score score2) {
        Integer score1asInt = Integer.parseInt(score1.getValue());
        Integer score2asInt = Integer.parseInt(score2.getValue());
        return score1asInt > score2asInt ? score1 : score2;
    }
    
}
