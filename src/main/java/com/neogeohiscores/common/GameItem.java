package com.neogeohiscores.common;

public class GameItem {

    private String name;
    private long id;
    private long count;
    private String genre;

    public GameItem(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public GameItem(String name, long id, long count, String genre) {
        this(name, id);
        this.count = count;
        this.setGenre(genre);
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

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
