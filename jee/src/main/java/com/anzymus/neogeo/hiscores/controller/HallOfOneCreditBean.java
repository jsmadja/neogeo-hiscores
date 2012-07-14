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

import static com.anzymus.neogeo.hiscores.common.IntegerToRank.getOrdinalFor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.halloffame.HallOfOneCreditService;

@ManagedBean
public class HallOfOneCreditBean {

	@EJB
	HallOfOneCreditService hallOfOneCreditService;

	private List<PlayerItem> playerItems;

	@PostConstruct
	public void init() {
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
			PlayerItem playerItem = createPlayerItem(player, getOrdinalFor(rank), points);
			items.add(playerItem);
			oldRank = rank;
			oldPoints = points;
		}
		return items;
	}

	private PlayerItem createPlayerItem(Player player, String rank, int points) {
		PlayerItem playerItem = new PlayerItem();
		playerItem.setRank(rank);
		playerItem.setFullname(player.getFullname());
		playerItem.setContribution(player.getContribution());
		return playerItem;
	}

}
