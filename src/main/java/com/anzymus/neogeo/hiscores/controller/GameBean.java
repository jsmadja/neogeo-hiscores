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
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class GameBean {

    @EJB
    ScoreService scoreService;

    @EJB
    GameService gameService;
    
    @ManagedProperty(value = "#{param.id}")
    private long id;

    private String name;
    
    private String pictureUrl;
    
    private String rules;
    
    private Scores scores;

    private static final String[] RANKS = { "1st", "2nd", "3rd" };

    private static Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();

    @PostConstruct
    public void init() {
        Game game = gameService.findById(id);
        name = game.getName();
        scores = scoreService.findAllByGame(game);
    }
    
    public String edit() {
        Game game = gameService.findById(id);
        game.setRules(rules);
        game.setPictureUrl(pictureUrl);
        gameService.store(game);
        return "home";
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public List<LevelItem> getLevels() {
        List<LevelItem> levelItems = new ArrayList<LevelItem>();
        for (Map.Entry<String, List<Score>> value : this.scores.getScoresByLevels().entrySet()) {
            String level = value.getKey();
            List<Score> scoreList = value.getValue();
            Collections.sort(scoreList, sortScoreByValueDesc);
            List<ScoreItem> scoreItems = createScoreItems(scoreList);
            LevelItem levelItem = new LevelItem(level);
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
                scoreItem.setPlayer(score.getPlayer().getFullname());
                scoreItem.setPictureUrl(score.getPictureUrl());
                scoreItem.setId(score.getId());
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

}
