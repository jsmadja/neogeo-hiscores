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

import java.util.*;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.neogeohiscores.comparator.ScoreComparator;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;

import javax.annotation.Nullable;

import static com.google.common.collect.Collections2.filter;
import static java.util.Collections.sort;

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

    public Score getBestScoreFor(final Player player, final String level, final Game game) {
        List<Score> filterScores = new ArrayList<Score>(filter(scores, new Predicate<Score>() {
            @Override
            public boolean apply(@Nullable Score input) {
                return input.getLevel().equals(level) && input.getPlayer().equals(player) && input.getGame().equals(game);
            }
        }));
        sort(filterScores, new ScoreSortedByValueDescComparator());
        if(filterScores.isEmpty()) {
            return null;
        }
        return filterScores.get(0);
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

    private List<Score> asSortedList(Comparator<Score> comparator) {
        List<Score> sortedScores = new ArrayList<Score>();
        sortedScores.addAll(scores);
        sort(sortedScores, comparator);
        return sortedScores;
    }

    public List<Score> asSortedList() {
        return asSortedList(new ScoreSortedByValueDescComparator());
    }

    public List<Score> getScoresByLevel(String level) {
        List<Score> scoresByLevel = new ArrayList<Score>();
        for (Score score : scores) {
            if (level.equals(score.getLevel())) {
                scoresByLevel.add(score);
            }
        }
        return scoresByLevel;
    }

}
