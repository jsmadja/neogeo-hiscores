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
import java.util.Map;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import javax.ejb.Stateless;

@Stateless
public class ScoreService {

    private Map<Game, Scores> scoresByGame = new HashMap<Game, Scores>();
    private Map<Player, Scores> scoresByPlayer = new HashMap<Player, Scores>();
    private Scores scores = new Scores();

    public void add(Score score) {
        addScoreInGameMap(score);
        addScoreInPlayerMap(score);
        scores.add(score);
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

    private void addScoreInPlayerMap(Score score) {
        Player player = score.getPlayer();
        Scores scores = scoresByPlayer.get(player);
        if (scores == null) {
            scores = new Scores();
            scoresByPlayer.put(player, scores);
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
        Scores scores = scoresByPlayer.get(player);
        if (scores == null) {
            scores = new Scores();
        }
        return scores;
    }

    public Scores findAll() {
        return scores;
    }

}
