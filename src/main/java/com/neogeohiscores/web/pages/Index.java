package com.neogeohiscores.web.pages;

import com.neogeohiscores.entities.Game;
import com.neogeohiscores.web.services.GameRoom;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index {

    @Inject
    private GameRoom gameRoom;

    public Game getGameOfTheDay() {
        return gameRoom.getGameOfTheDay();
    }

}
