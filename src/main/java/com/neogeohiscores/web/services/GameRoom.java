package com.neogeohiscores.web.services;

import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.joda.time.DateMidnight;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {

    @Inject
    private Session session;

    public Game getGameOfTheDay() {
        DateMidnight dateTime = new DateMidnight();
        List<Game> games = findAllGames();
        int num = (int) ((dateTime.getYear() * dateTime.getDayOfMonth() * dateTime.getMonthOfYear()) % games.size());
        Game gameOfTheDay = games.get(num);
        return gameOfTheDay;
    }

    public List<Game> findAllGames() {
        return session.createCriteria(Game.class).addOrder(Order.asc("name")).setCacheRegion("games").setCacheable(true).list();
    }

    public Player findByFullname(String fullname) {
        for (Player player : findAllPlayers()) {
            if (player.isCalled(fullname)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> findAllPlayers() {
        return session.createCriteria(Player.class).setCacheable(true).list();
    }

    public List<Game> findGamesByGenre(String genre) {
        List<Game> games = new ArrayList<Game>();
        for (Game game : findAllGames()) {
            if (game.getGenre().equalsIgnoreCase(genre)) {
                games.add(game);
            }
        }
        return games;
    }

    public Game findByName(String gameName) {
        for (Game game : findAllGames()) {
            if (game.getName().equalsIgnoreCase(gameName)) {
                return game;
            }
        }
        throw new IllegalArgumentException("Aucun jeu nommé '" + gameName + "'");
    }

    public void merge(Player player) {
        session.saveOrUpdate(player);
    }

    public Player store(Player player) {
        return (Player) session.merge(player);
    }
}
