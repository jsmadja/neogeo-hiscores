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

public class LevelItem {

    private String label;
    private Set<String> scores = new HashSet<String>();
    private List<ScoreItem> scoreItems;

    public LevelItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void addScore(String value) {
        scores.add(value);
    }

    public List<String> getScores() {
        List<String> sortedScores = new ArrayList<String>();
        sortedScores.addAll(scores);
        Collections.sort(sortedScores, sortByValueDescComparator);
        return sortedScores;
    }
    private static Comparator<String> sortByValueDescComparator = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return s2.compareTo(s1);
        }
    };

    public void setScoreItems(List<ScoreItem> scoreItems) {
        this.scoreItems = scoreItems;
    }

    public List<ScoreItem> getScoreItems() {
        return scoreItems;
    }
    
}
