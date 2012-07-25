package com.neogeohiscores.web.pages;

import com.neogeohiscores.common.IntegerToRank;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.PlayerItem;
import com.neogeohiscores.web.services.halloffame.HallOfOneCreditService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class HallOf1cc {

    @Inject
    private HallOfOneCreditService hallOfOneCreditService;

    @Property
    private PlayerItem playerItem;

    private List<PlayerItem> playerItems;

    void onActivate() {
        List<Player> players = hallOfOneCreditService.getPlayersOrderByAllClearCount();
        playerItems = createPlayerItems(players);
    }

    public List<PlayerItem> getPlayers() {
        return playerItems;
    }

    private List<PlayerItem> createPlayerItems(List<Player> players) {
        List<PlayerItem> items = new ArrayList<PlayerItem>();
        int oldRank = Integer.MIN_VALUE;
        int oldPoints = 0;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int rank = i + 1;
            int points = player.getPoints();
            if (oldRank != Integer.MIN_VALUE && oldPoints == points) {
                rank = oldRank;
                points = oldPoints;
            }
            PlayerItem playerItem = createPlayerItem(player, IntegerToRank.getOrdinalFor(rank), points);
            items.add(playerItem);
            oldRank = rank;
            oldPoints = points;
        }
        return items;
    }

    private PlayerItem createPlayerItem(Player player, String rank, int points) {
        PlayerItem playerItem = new PlayerItem();
        playerItem.setRank(rank);
        playerItem.setPlayer(player);
        playerItem.setContribution(player.getContribution());
        return playerItem;
    }

}
