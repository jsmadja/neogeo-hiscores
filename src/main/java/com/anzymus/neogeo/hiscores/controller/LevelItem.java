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

package com.anzymus.neogeo.hiscores.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.anzymus.neogeo.hiscores.domain.Score;

public class LevelItem {

    private String label;
    private Set<Score> scores = new HashSet<Score>();
    private List<ScoreItem> scoreItems;

    public LevelItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public List<Score> getScores() {
        List<Score> sortedScores = new ArrayList<Score>();
        sortedScores.addAll(scores);
        Collections.sort(sortedScores, sortByValueDescComparator);
        return sortedScores;
    }

    private static Comparator<Score> sortByValueDescComparator = new Comparator<Score>() {
        @Override
        public int compare(Score s1, Score s2) {
            return s2.getValue().compareTo(s1.getValue());
        }
    };

    public void setScoreItems(List<ScoreItem> scoreItems) {
        this.scoreItems = scoreItems;
    }

    public List<ScoreItem> getScoreItems() {
        return scoreItems;
    }

}
