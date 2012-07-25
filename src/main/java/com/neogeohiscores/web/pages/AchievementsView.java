package com.neogeohiscores.web.pages;

import com.neogeohiscores.entities.Achievement;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.web.services.TitleBoard;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AchievementsView {

    @Persist
    @Property
    private Player player;

    @Property
    private Achievement achievement;

    void onActivate(Player player) {
        this.player = player;
    }

    @Inject
    private TitleBoard titleBoard;

    public List<Achievement> getAchievements() {
        List<Achievement> achievements = new ArrayList<Achievement>();
        achievements.addAll(titleBoard.getAchievementsFor(player));
        Collections.sort(achievements, new Comparator<Achievement>() {
            @Override
            public int compare(Achievement a1, Achievement a2) {
                return a2.getProgressInPercent() - a1.getProgressInPercent();
            }
        });
        return achievements;
    }

}