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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.neogeohiscores.comparator.ScoreComparator;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;

public class Scores implements Iterable<Score> {

    private Set<Score> scores = new HashSet<Score>();

    public void add(Score score) {
        String level = score.getLevel();
        Player player = score.getPlayer();
        Game game = score.getGame();
        Score bestScore = getBestScoreFor(player, level, game);
        if (bestScore == null) {
            scores.add(score);
        } else {
            Score maxScore = ScoreComparator.max(score, bestScore);
            scores.remove(bestScore);
            scores.add(maxScore);
        }
    }

    public Score getBestScoreFor(Player player, String level, Game game) {
        for (Score score : scores) {
            if (score.getLevel().equals(level) && score.getPlayer().equals(player) && score.getGame().equals(game)) {
                return score;
            }
        }
        return null;
    }

    public boolean contains(Score score) {
        return scores.contains(score);
    }

    public int count() {
        return scores.size();
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

    public List<Score> asSortedList() {
        return asSortedList(comparatorByValueDesc);
    }

    private static Comparator<Score> comparatorByDateDesc = new Comparator<Score>() {
        @Override
        public int compare(Score s1, Score s2) {
            return s2.getCreationDate().compareTo(s1.getCreationDate());
        }
    };

    private static Comparator<Score> comparatorByValueDesc = new ScoreSortedByValueDescComparator();

    public List<Score> getScoresByLevel(String level) {
        List<Score> scoresByLevel = new ArrayList<Score>();
        for (Score score : scores) {
            if (level.equals(score.getLevel())) {
                scoresByLevel.add(score);
            }
        }
        return scoresByLevel;
    }

    public int getRank(Player player) {
        keepOnlyLevel("MVS");
        List<Score> asSortedList = asSortedList();
        for (int i = 0; i < asSortedList.size(); i++) {
            if (asSortedList.get(i).getPlayer().equals(player)) {
                return i + 1;
            }
        }
        return Integer.MAX_VALUE;
    }

    private void keepOnlyLevel(final String level) {
        Predicate<Score> predicate = new Predicate<Score>() {
            @Override
            public boolean apply(Score score) {
                return score.getLevel().equals(level);
            }
        };
        scores = Sets.filter(scores, predicate);
    }

}
