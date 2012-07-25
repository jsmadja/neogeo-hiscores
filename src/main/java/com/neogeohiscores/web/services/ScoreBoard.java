package com.neogeohiscores.web.services;

import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Scores;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    @Inject
    private Session session;

    @Inject
    private GameRoom gameRoom;

    public List<Score> findLastScoresOrderByDateDesc() {
        return session.createCriteria(Score.class).addOrder(Order.desc("creationDate")).setCacheable(true).setMaxResults(10).list();
    }

    public Score store(Score score) {
        Game gameOfTheDay = gameRoom.getGameOfTheDay();
        int bonusFactor = 1;
        boolean isGameOfTheDay = score.getGame().equals(gameOfTheDay);
        if (isGameOfTheDay) {
            bonusFactor = 2;
        }
        int rank = score.getNumRankOf();
        int totalScore = score.getGame().getScores(score.getLevel()).count();
        int rankFactor = totalScore - rank + 1;
        int nghPoints = bonusFactor * rankFactor;

        score.setNghPoints(nghPoints);
        score.setGameOfTheDay(isGameOfTheDay);
        score.setRank(rank);
        return (Score) session.merge(score);
    }

    public List<Scores> getScoresByGameGenre(String genre) {
        List<Scores> scores = new ArrayList<Scores>();
        List<Game> games = gameRoom.findGamesByGenre(genre);
        for (Game game : games) {
            scores.add(game.getScores());
        }
        return scores;
    }

}
