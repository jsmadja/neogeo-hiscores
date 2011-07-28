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
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class PlayerBean {

    @EJB
    ScoreService scoreService;

    @EJB
    PlayerService playerService;

    @EJB
    GameService gameService;

    @ManagedProperty(value = "#{param.fullname}")
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<ScoreItem> getScores() {
        List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();
        Player player = playerService.findByFullname(fullname);
        for (Game game : gameService.findAllGamesPlayedBy(player)) {
            extractScoreItemsFromGame(scoreItems, game);
        }
        return scoreItems;
    }

    private void extractScoreItemsFromGame(List<ScoreItem> scoreItems, Game game) {
        Scores scores = scoreService.findAllByGame(game);
        for (String level : Levels.list()) {
            extractScoreItemsFromLevels(scoreItems, game, scores, level);
        }
    }

    private void extractScoreItemsFromLevels(List<ScoreItem> scoreItems, Game game, Scores scores, String level) {
        List<Score> scoreList = scores.getScoresByLevel(level);
        Collections.sort(scoreList, new ScoreSortedByValueDescComparator());
        Score oldScore = null;
        int oldRank = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            int rank = i + 1;
            if (score.getPlayer().getFullname().equals(fullname)) {
                String value = score.getValue();
                if (oldScore != null && oldScore.getValue().equals(value)) {
                    rank = oldRank;
                }
                ScoreItem scoreItem = new ScoreItem();
                scoreItem.setRankNumber(rank);
                scoreItem.setValue(value);
                scoreItem.setLevel(level);
                scoreItem.setGame(game);
                scoreItem.setId(score.getId());
                scoreItem.setPictureUrl(score.getPictureUrl());
                scoreItems.add(scoreItem);
            }
            oldScore = score;
            oldRank = rank;
        }
    }

}
