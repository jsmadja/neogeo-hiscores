/**
 *     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.anzymus.neogeo.hiscores.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.halloffame.HallOfFameService;

@ManagedBean
public class HallOfFameBean {

    @EJB
    HallOfFameService hallOfFameService;

    @ManagedProperty(value = "#{param.level}")
    private String level;

    private String mask = new String("#0.##");
    private DecimalFormat format = new DecimalFormat(mask);

    public List<PlayerItem> getPlayers() {
        List<Player> players = hallOfFameService.getPlayersOrderByRank(level);
        return createPlayerItems(players);
    }

    public List<PlayerItem> getPlayersV2() {
        List<Player> players = hallOfFameService.getPlayersOrderByRankV2(level);
        return createPlayerItems(players);
    }

    private List<PlayerItem> createPlayerItems(List<Player> players) {
        if (level == null) {
            level = "MVS";
        }
        List<PlayerItem> playerItems = new ArrayList<PlayerItem>();
        String oldRank = null;
        int oldPoints = 0;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            PlayerItem playerItem = new PlayerItem();
            String rank = Integer.toString(i + 1);
            int points = player.getPoints();
            if (oldRank != null && oldPoints == points) {
                rank = oldRank;
                points = oldPoints;
            }
            playerItem.setRank(rank);
            playerItem.setFullname(player.getFullname());
            playerItem.setScore(points);
            playerItem.setContribution(player.getContribution());
            double average = 0;
            if (player.getContribution() != 0) {
                average = (double) points / (double) player.getContribution();
            }
            playerItem.setAverage(format.format(average));
            playerItems.add(playerItem);
            oldRank = rank;
            oldPoints = points;
        }
        return playerItems;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public List<String> getLevels() {
        return Levels.list();
    }

}
