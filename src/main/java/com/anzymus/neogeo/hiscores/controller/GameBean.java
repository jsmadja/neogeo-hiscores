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
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Level;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class GameBean {

    @EJB
    ScoreService scoreService;

    @ManagedProperty(value = "#{param.name}")
    private String name;

    private Scores scores;

    private static final String[] RANKS = { "1st", "2nd", "3rd" };

    private static Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();

    @PostConstruct
    public void init() {
        Game game = new Game(name);
        scores = scoreService.findAllByGame(game);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LevelItem> getLevels() {
        List<LevelItem> levelItems = new ArrayList<LevelItem>();
        for (Map.Entry<Level, List<Score>> value : this.scores.getScoresByLevels().entrySet()) {
            Level level = value.getKey();
            List<Score> scores = value.getValue();
            Collections.sort(scores, sortScoreByValueDesc);
            List<ScoreItem> scoreItems = createScoreItems(scores);
            LevelItem levelItem = new LevelItem(level.getLabel());
            levelItem.setScoreItems(scoreItems);
            levelItems.add(levelItem);
        }
        return levelItems;
    }

    private List<ScoreItem> createScoreItems(List<Score> scores) {
        List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();
        for (int i = 0; i < 10; i++) {
            ScoreItem scoreItem = new ScoreItem();
            if (i < scores.size()) {
                Score score = scores.get(i);
                scoreItem.setValue(score.getValue());
                scoreItem.setPlayer(score.getPlayer().getShortname());
                scoreItem.setPictureUrl(score.getPictureUrl());
            } else {
                scoreItem.setValue("");
                scoreItem.setPlayer("");
            }
            if (i <= 2) {
                scoreItem.setRank(RANKS[i]);
            } else {
                scoreItem.setRank((i + 1) + "th");
            }
            scoreItems.add(scoreItem);
        }
        return scoreItems;
    }

}
