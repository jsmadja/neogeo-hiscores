package com.neogeohiscores.web.pages;

import com.neogeohiscores.common.IntegerToRank;
import com.neogeohiscores.common.Levels;
import com.neogeohiscores.comparator.PlayerItemsSortedWithAverageComparator;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.PlayerItem;
import com.neogeohiscores.web.services.halloffame.HallOfFameService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HallOfFame {

    @Inject
    private HallOfFameService hallOfFameService;

    @Property
    private PlayerItem playerItem;

    private DecimalFormat format = new DecimalFormat("#0.##");

    private List<PlayerItem> playerItems;

    private Comparator<PlayerItem> sortPlayerItemsWithAverageComparator = new PlayerItemsSortedWithAverageComparator();

    void onActivate() {
        List<Player> players = hallOfFameService.getPlayersOrderByRank("MVS");
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
        Collections.sort(items, sortPlayerItemsWithAverageComparator);
        return items;
    }

    private PlayerItem createPlayerItem(Player player, String rank, int points) {
        PlayerItem playerItem = new PlayerItem();
        playerItem.setRank(rank);
        playerItem.setScore(points);
        playerItem.setPlayer(player);
        playerItem.setContribution(player.getContribution());
        double average = 0;
        if (player.getContribution() != 0) {
            average = (double) points / (double) player.getContribution();
        }
        playerItem.setAverage(format.format(average));
        return playerItem;
    }

    public List<String> getLevels() {
        return Levels.list();
    }

}
