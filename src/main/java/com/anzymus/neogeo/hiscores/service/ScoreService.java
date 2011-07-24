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

package com.anzymus.neogeo.hiscores.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;

@Stateless
public class ScoreService {
    public static final int MAX_SCORES_TO_RETURN = 20;

    private static final Map<Game, Scores> scoresByGame = new HashMap<Game, Scores>();
    private static final Map<String, Scores> scoresByPlayer = new HashMap<String, Scores>();
    private static final Map<Integer, Score> scoresById = new HashMap<Integer, Score>();
    private static final Scores scores = new Scores();

    public void add(Score score) {
        if (!scores.contains(score)) {
            scores.add(score);
            addScoreInGameMap(score);
            addScoreInPlayerMap(score);
            addScoreInIdMap(score);
        }
    }

    private void addScoreInGameMap(Score score) {
        Game game = score.getGame();
        Scores scores = scoresByGame.get(game);
        if (scores == null) {
            scores = new Scores();
            scoresByGame.put(game, scores);
        }
        scores.add(score);
    }

    private void addScoreInIdMap(Score score) {
        scoresById.put(score.getId(), score);
    }

    private void addScoreInPlayerMap(Score score) {
        Player player = score.getPlayer();
        String fullname = player.getFullname().toUpperCase();
        Scores scores = scoresByPlayer.get(fullname);
        if (scores == null) {
            scores = new Scores();
            scoresByPlayer.put(fullname, scores);
        }
        scores.add(score);
    }

    public Scores findAllByGame(Game game) {
        Scores scores = scoresByGame.get(game);
        if (scores == null) {
            scores = new Scores();
        }
        return scores;
    }

    public Scores findAllByPlayer(Player player) {
        Scores scores = scoresByPlayer.get(player.getFullname().toUpperCase());
        if (scores == null) {
            scores = new Scores();
        }
        return scores;
    }

    public Scores findAll() {
        return scores;
    }

    public List<Score> findLastScoresOrderByDateDesc() {
        List<Score> sortedScores = scores.sortByDateDesc();
        int end = MAX_SCORES_TO_RETURN > sortedScores.size() ? sortedScores.size() : MAX_SCORES_TO_RETURN;
        return sortedScores.subList(0, end);
    }

    public Score findById(int id) {
        return scoresById.get(id);
    }

}
