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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.HallOfFameService;

@ManagedBean
public class HallOfFameBean {

    @EJB
    HallOfFameService hallOfFameService;

    public List<PlayerItem> getPlayers() {
        List<PlayerItem> playerItems = new ArrayList<PlayerItem>();
        List<Player> players = hallOfFameService.getPlayersOrderByRank();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            PlayerItem playerItem = new PlayerItem();
            playerItem.setRank(Integer.toString(i + 1));
            playerItem.setShortname(player.getShortname());
            playerItem.setFullname(player.getFullname());
            playerItem.setScore(player.getPoints());
            playerItem.setContribution(player.getContribution());
            playerItems.add(playerItem);
        }
        return playerItems;
    }
}
