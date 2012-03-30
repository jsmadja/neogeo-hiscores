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

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.anzymus.neogeo.hiscores.domain.Achievement;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.PlayerService;

@ManagedBean
public class AchievementsBean {

	@ManagedProperty(value = "#{param.playerId}")
	private String playerId;

	@EJB
	private PlayerService playerService;

	private List<Achievement> achievements;
	private Player player;

	@PostConstruct
	public void init() {
		player = playerService.findById(Long.parseLong(playerId));
		List<Achievement> achievements = playerService.getAchievementsFor(player);
		java.util.Collections.sort(achievements, new Comparator<Achievement>() {
			@Override
			public int compare(Achievement a1, Achievement a2) {
				return a2.getProgressInPercent() - a1.getProgressInPercent();
			}
		});
		setAchievements(achievements);
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerId() {
		return playerId;
	}

}
