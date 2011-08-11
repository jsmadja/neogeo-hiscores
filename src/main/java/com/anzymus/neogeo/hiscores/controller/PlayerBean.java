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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.anzymus.neogeo.hiscores.comparator.ScoreComparator;
import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import com.anzymus.neogeo.hiscores.service.TitleService;
import java.util.TreeSet;
import javax.annotation.PostConstruct;

@ManagedBean
public class PlayerBean {

    @EJB
    ScoreService scoreService;
    
    @EJB
    PlayerService playerService;
    
    @EJB
    GameService gameService;
    
    @EJB
    TitleService titleService;
    
    @ManagedProperty(value = "#{param.fullname}")
    private String fullname;
    
    private Collection<TitleItem> titleItems = new ArrayList<TitleItem>();
    
    private List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();

    private Player player;
    
    @PostConstruct
    public void init() {
        player = playerService.findByFullname(fullname);
        loadTitleItems();
        loadScoreItems();
    }

    private void loadScoreItems() {
        for (Game game : gameService.findAllGamesPlayedBy(player)) {
            extractScoreItemsFromGame(scoreItems, game);
        }
    }

    private void loadTitleItems() {
        Set<Title> titles = new TreeSet<Title>(titleService.findAllStrategies().keySet());
        for (Title title : titles) {
            boolean isUnlocked = player.hasUnlocked(title);
            TitleItem titleItem = new TitleItem(title, isUnlocked);
            titleItems.add(titleItem);
        }
    }

    public Collection<TitleItem> getTitles() {
        return titleItems;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<ScoreItem> getScores() {
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
        Score previousScore = null;
        Score nextScore = null;
        int previousRank = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            nextScore = (i + 1) < scoreList.size() ? scoreList.get(i + 1) : null;
            int rank = i + 1;
            if (isCurrentPlayer(score)) {
                String value = score.getValue();
                if (isSameRankAsPreviousScore(previousScore, value)) {
                    rank = previousRank;
                }
                Long scoreId = score.getId();
                String scorePictureUrl = score.getPictureUrl();
                ScoreItem scoreItem = createScoreItem(game, level, rank, value, scoreId, scorePictureUrl);
                scoreItem.setPositiveGap(computeGap(value, nextScore));
                scoreItem.setNegativeGap(computeGap(value, previousScore));
                scoreItems.add(scoreItem);
            }
            previousScore = score;
            previousRank = rank;
        }
    }

    private boolean isCurrentPlayer(Score score) {
        return score.getPlayer().getFullname().equals(fullname);
    }

    private ScoreItem createScoreItem(Game game, String level, int rank, String value, Long scoreId,
            String scorePictureUrl) {
        ScoreItem scoreItem = new ScoreItem();
        scoreItem.setRankNumber(rank);
        scoreItem.setValue(value);
        scoreItem.setLevel(level);
        scoreItem.setGame(game);
        scoreItem.setId(scoreId);
        scoreItem.setPictureUrl(scorePictureUrl);
        return scoreItem;
    }

    private String computeGap(String value, Score score) {
        String gap = "";
        if (score != null) {
            gap = ScoreComparator.gap(value, score.getValue());
        }
        return gap;
    }

    private boolean isSameRankAsPreviousScore(Score previousScore, String scoreValue) {
        return previousScore != null && previousScore.getValue().equals(scoreValue);
    }
}
