package com.anzymus.neogeo.hiscores.comparator;

import java.util.Comparator;
import com.anzymus.neogeo.hiscores.domain.Player;

public class PlayerSortedByPointsDescComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        return p2.getPoints() - p1.getPoints();
    }
}
