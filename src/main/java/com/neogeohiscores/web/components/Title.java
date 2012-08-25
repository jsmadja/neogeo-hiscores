package com.neogeohiscores.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.neogeohiscores.entities.Player;

public class Title {

    @Property
    @Parameter
    private com.neogeohiscores.entities.Title title;

    @Parameter
    private Player player;

    public boolean isUnlocked() {
        return player.hasUnlocked(title);
    }

}
