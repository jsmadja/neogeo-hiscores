package com.neogeohiscores.web.pages;

import com.neogeohiscores.common.Levels;
import com.neogeohiscores.common.Players;
import com.neogeohiscores.common.ScoreItem;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Scores;
import com.neogeohiscores.web.services.GameService;
import com.neogeohiscores.web.services.ScoreService;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class PlayerView {

    @Inject
    private GameService gameService;

    @Inject
    private ScoreService scoreService;

    @Persist
    @Property
    private Player player;

    @Property
    private ScoreItem score;

    private List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();

    private List<ScoreItem> scoreItemsOneCredit = new ArrayList<ScoreItem>();

    void onActivate(Player player) {
        this.player = player;
    }

    @SetupRender
    public void init() {
        loadScoreItems();
    }

    private void loadScoreItems() {
        scoreItems = new ArrayList<ScoreItem>();
        for (Game game : gameService.findAllGamesPlayedBy(player)) {
            extractScoreItemsFromGame(scoreItems, game);
        }
    }

    public List<ScoreItem> getScores() {
        return scoreItems;
    }

    public List<ScoreItem> getScoresOneCredit() {
        return scoreItemsOneCredit;
    }

    private void extractScoreItemsFromGame(List<ScoreItem> scoreItems, Game game) {
        Scores scores = scoreService.findAllByGame(game);
        for (String level : Levels.list()) {
            Players.extractScoreItemsFromLevels(scoreItems, game, scores, level, player.getFullname());
        }
    }

}
