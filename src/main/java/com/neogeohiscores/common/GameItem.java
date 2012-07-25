package com.neogeohiscores.common;

import com.neogeohiscores.entities.Game;

import java.util.List;

public class GameItem {

    private String name;
    private long id;
    private long count;
    private Game game;
    private String genre;
    private List<ScoreItem> scores;

    public GameItem(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public GameItem(String name, long id, long count, String genre) {
        this(name, id);
        this.count = count;
        this.setGenre(genre);
    }

    public GameItem(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getCount() {
        return count;
    }

    public Game getGame() {
        return game;
    }

    public void setScores(List<ScoreItem> scores) {
        this.scores = scores;
    }

    public List<ScoreItem> getScores() {
        return scores;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
