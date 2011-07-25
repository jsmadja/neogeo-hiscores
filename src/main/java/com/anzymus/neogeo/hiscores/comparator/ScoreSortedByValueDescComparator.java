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

import java.util.Comparator;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreSortedByValueDescComparator implements Comparator<Score> {
    @Override
    public int compare(Score s1, Score s2) {
        String value1 = s1.getValue();
        String value2 = s2.getValue();
        value1 = value1.replaceAll("\\.", "");
        value2 = value2.replaceAll("\\.", "");
        Integer score1 = Integer.parseInt(value1);
        Integer score2 = Integer.parseInt(value2);
        return score2.compareTo(score1);
    }
}