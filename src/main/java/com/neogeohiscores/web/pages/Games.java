package com.neogeohiscores.web.pages;

import com.neogeohiscores.entities.Game;
import com.neogeohiscores.web.services.GameRoom;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Games {

    @Inject
    private GameRoom gameRoom;

    @Property
    private Game game;

    public List<Game> getGames() {
        return gameRoom.findAllGames();
    }

}
