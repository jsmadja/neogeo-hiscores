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

import com.anzymus.neogeo.hiscores.domain.Game;
import java.util.Comparator;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreSortedByValueDescComparator implements Comparator<Score> {
    @Override
    public int compare(Score s1, Score s2) {
        Game game = s1.getGame();
        String gameName = game.getName();
        String value1 = s1.getValue();
        String value2 = s2.getValue();
        
        int comparison = 0;
        if (gameName.equals("Neo Turf Masters / Big Tournament Golf")) {
            comparison = compareAsNegativeInt(value1, value2);
        } else {
            comparison = compareAsInt(value1, value2);
        }
        return comparison;
    }
    
    private int compareAsInt(String value1, String value2) {
        Integer score1 = Integer.parseInt(value1);
        Integer score2 = Integer.parseInt(value2);
        return score2.compareTo(score1);
    }
    
    private int compareAsNegativeInt(String value1, String value2) {
        value1 = value1.replaceAll("-", "");
        value2 = value2.replaceAll("-", "");
        return compareAsInt(value1, value2);
    }
}
