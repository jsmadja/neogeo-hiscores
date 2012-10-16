package com.neogeohiscores.web.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.neogeohiscores.common.LevelItem;
import com.neogeohiscores.common.Levels;
import com.neogeohiscores.common.ScoreItem;
import com.neogeohiscores.common.ScoreItems;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Scores;
import com.neogeohiscores.web.services.GameService;
import com.neogeohiscores.web.services.ScoreService;

import static com.neogeohiscores.common.ScoreItems.createScoreItems;
import static java.util.Collections.sort;

public class GameView {

    private static final int MIN_SCORE_TO_SHOW = 10;

    @Property
    private LevelItem level;

    @Property
    private ScoreItem score;

    @Inject
    private ScoreService scoreService;

    @Inject
    private GameService gameService;

    @Persist
    @Property
    private Game game;

    private Scores scores;

    private static Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();

    private List<LevelItem> levelItems = new ArrayList<LevelItem>();

    void onActivate(Game game) {
        this.game = game;
        scores = scoreService.findAllByGame(game);
        loadLevelItems();
    }

    private void loadLevelItems() {
        levelItems.clear();
        for (String level : Levels.list()) {
            List<Score> scoreList = scores.getScoresByLevel(level);
            if (!scoreList.isEmpty()) {
                sort(scoreList, sortScoreByValueDesc);
                List<ScoreItem> scoreItems = createScoreItems(scoreList, MIN_SCORE_TO_SHOW);
                LevelItem levelItem = new LevelItem(level);
                levelItem.setScoreItems(scoreItems);
                levelItems.add(levelItem);
            }
        }
    }

    public List<LevelItem> getLevels() {
        return levelItems;
    }

}
