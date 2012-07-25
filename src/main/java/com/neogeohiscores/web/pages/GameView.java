package com.neogeohiscores.web.pages;

import com.neogeohiscores.common.LevelItem;
import com.neogeohiscores.common.Levels;
import com.neogeohiscores.common.ScoreItem;
import com.neogeohiscores.common.ScoreItems;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Scores;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameView {

    private static final int MIN_SCORE_TO_SHOW = 10;

    @Property
    private LevelItem level;

    @Property
    private ScoreItem score;

    @Persist
    @Property
    private Game game;

    private Scores scores;

    void onActivate(Game game) {
        this.game = game;
    }

    public List<LevelItem> getLevels() {
        scores = game.getScores();
        List<LevelItem> levelItems = new ArrayList<LevelItem>();
        Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();
        for (String level : Levels.list()) {
            List<Score> scoreList = scores.getScoresByLevel(level);
            if (!scoreList.isEmpty()) {
                Collections.sort(scoreList, sortScoreByValueDesc);
                List<ScoreItem> scoreItems = ScoreItems.createScoreItems(scoreList, MIN_SCORE_TO_SHOW);
                LevelItem levelItem = new LevelItem(level);
                levelItem.setScoreItems(scoreItems);
                levelItems.add(levelItem);
            }
        }
        return levelItems;
    }

}
