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

package com.anzymus.neogeo.hiscores.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scores implements Iterable<Score> {

    private Set<Score> scores = new HashSet<Score>();
    private Map<String, List<Score>> scoresByLevels = new HashMap<String, List<Score>>();

    public void add(Score score) {
        scores.add(score);
        String level = score.getLevel();
        List<Score> scoreList = scoresByLevels.get(level);
        if (scoreList == null) {
            scoreList = new ArrayList<Score>();
            scoresByLevels.put(level, scoreList);
        }
        scoreList.add(score);
    }

    public boolean contains(Score score) {
        return scores.contains(score);
    }

    public int count() {
        return scores.size();
    }

    public Map<String, List<Score>> getScoresByLevels() {
        return scoresByLevels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Score score : scores) {
            sb.append(score).append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator<Score> iterator() {
        return scores.iterator();
    }

    public List<Score> sortByDateDesc() {
        return asSortedList(comparatorByDateDesc);
    }

    private List<Score> asSortedList(Comparator<Score> comparator) {
        List<Score> sortedScores = new ArrayList<Score>();
        sortedScores.addAll(scores);
        Collections.sort(sortedScores, comparator);
        return sortedScores;
    }
    private static Comparator<Score> comparatorByDateDesc = new Comparator<Score>() {

        @Override
        public int compare(Score s1, Score s2) {
            return s2.getCreationDate().compareTo(s1.getCreationDate());
        }
    };
}
