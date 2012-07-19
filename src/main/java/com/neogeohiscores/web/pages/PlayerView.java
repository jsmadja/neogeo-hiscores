package com.neogeohiscores.web.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;
import com.neogeohiscores.common.Levels;
import com.neogeohiscores.common.Players;
import com.neogeohiscores.common.ScoreItem;
import com.neogeohiscores.common.ScoreItems;
import com.neogeohiscores.common.TitleItem;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Scores;
import com.neogeohiscores.entities.Title;
import com.neogeohiscores.web.services.GameService;
import com.neogeohiscores.web.services.ScoreService;
import com.neogeohiscores.web.services.TitleService;

public class PlayerView {

    @Inject
    private GameService gameService;

    @Inject
    private TitleService titleService;

    @Inject
    private ScoreService scoreService;

    @Persist
    @Property
    private Player player;

    @Property
    private TitleItem titleItem;

    @Property
    private ScoreItem score;

    private List<TitleItem> titleItems = new ArrayList<TitleItem>();

    private List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();

    private List<ScoreItem> scoreItemsOneCredit = new ArrayList<ScoreItem>();

    void onActivate(Player player) {
        this.player = player;
    }

    @SetupRender
    public void init() {
        loadTitleItems();
        loadScoreItems();
    }

    private void loadScoreItems() {
        scoreItems = new ArrayList<ScoreItem>();
        for (Game game : gameService.findAllGamesPlayedBy(player)) {
            extractScoreItemsFromGame(scoreItems, game);
        }
        ScoreItems.discoverImprovableScores(scoreItems);
    }

    private void loadTitleItems() {
        titleItems = new ArrayList<TitleItem>();
        Map<Title, TitleUnlockingStrategy> strategies = titleService.findAllStrategies();
        Set<Title> allTitles = strategies.keySet();
        Set<Title> titles = new TreeSet<Title>(allTitles);
        for (Title title : titles) {
            boolean isUnlocked = player.hasUnlocked(title);
            TitleItem titleItem = new TitleItem(title, isUnlocked);
            titleItems.add(titleItem);
        }
        Collections.sort(titleItems);
    }

    public List<TitleItem> getTitlesLeft() {
        return titleItems.subList(0, titleItems.size() / 2);
    }

    public List<TitleItem> getTitlesRight() {
        return titleItems.subList(titleItems.size() / 2, titleItems.size());
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

    public boolean isUnlocked() {
        return player.hasUnlocked(titleItem.getTitle());
    }

}
