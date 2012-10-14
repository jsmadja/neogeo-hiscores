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
package com.neogeohiscores.web.services;

import com.neogeohiscores.common.IntegerToRank;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Scores;
import org.hibernate.Query;

import java.util.Collections;
import java.util.List;

public class ScoreService extends GenericService<Score> {

    private static final int MAX_SCORES_TO_RETURN = 10;

    public ScoreService() {
        super(Score.class);
    }

    public Scores findAllByGame(Game game) {
        Query query = session.getNamedQuery("score_findAllByGame");
        query.setParameter("game", game);
        List<Score> scores = query.list();
        return toScores(scores);
    }

    public Scores findAllByGame(Game game, String level) {
        Query query = session.getNamedQuery("score_findAllByGameAndLevel");
        query.setParameter("game", game);
        query.setParameter("level", level);
        List<Score> scores = query.list();
        return toScores(scores);
    }

    public Scores findAll() {
        Query query = session.getNamedQuery("score_findAll");
        List<Score> scores = query.list();
        return toScores(scores);
    }

    public List<Score> findLastScoresOrderByDateDesc() {
        Query query = session.getNamedQuery("score_findAllOrderByDateDesc");
        query.setMaxResults(MAX_SCORES_TO_RETURN);
        return query.list();
    }

    private Scores toScores(List<Score> scoreList) {
        Scores scores = new Scores();
        if (scoreList != null) {
            for (Score score : scoreList) {
                scores.add(score);
            }
        }
        return scores;
    }

    public String getRankOf(Score score) {
        int rank = getNumRankOf(score);
        if(rank == 0) {
            return null;
        }
        return IntegerToRank.getOrdinalFor(rank);
    }

    public int getNumRankOf(Score score) {
        Player player = score.getPlayer();
        Game game = score.getGame();
        String level = score.getLevel();
        Scores scores = findAllByGame(game, level);
        List<Score> scoreList = scores.asSortedList();
        Collections.sort(scoreList, new ScoreSortedByValueDescComparator());
        for (int i = 0; i < scoreList.size(); i++) {
            Score score_ = scoreList.get(i);
            int rank = i + 1;
            if (score_.getPlayer().equals(player)) {
                return rank;
            }
        }
        return scoreList.size()+1;
    }

    public Score store(Score score) {
        int rank = getNumRankOf(score);
        score.setRank(rank);
        return super.store(score);
    }

}