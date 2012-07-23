package com.neogeohiscores.web.pages;

import com.neogeohiscores.common.GameItem;
import com.neogeohiscores.web.services.GameService;
import com.neogeohiscores.web.services.ScoreService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Games {

    @Inject
    private GameService gameService;

    @Inject
    private ScoreService scoreService;

    @Property
    private GameItem gameItem;

    private List<GameItem> gameItems = new ArrayList<GameItem>();

    void onActivate() {
        gameItems.clear();
        loadGameItems();
    }

    private void loadGameItems() {
        List<Object[]> scoreCounts = gameService.findAllScoreCountForEachGames();
        for (Object[] scoreCount : scoreCounts) {
            Long gameId = ((BigInteger) scoreCount[0]).longValue();
            String gameName = (String) scoreCount[1];
            Long count = ((BigInteger) scoreCount[2]).longValue();
            String genre = (String) scoreCount[3];
            GameItem gameItem = new GameItem(gameName, gameId, count, genre);
            gameItems.add(gameItem);
        }
    }

    public List<GameItem> getGames() {
        return gameItems;
    }

}
