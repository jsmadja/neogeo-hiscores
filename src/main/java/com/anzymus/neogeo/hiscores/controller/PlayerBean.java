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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import com.anzymus.neogeo.hiscores.service.TitleService;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

@ManagedBean
public class PlayerBean {

	@EJB
	ScoreService scoreService;

	@EJB
	PlayerService playerService;

	@EJB
	GameService gameService;

	@EJB
	TitleService titleService;

	@ManagedProperty(value = "#{param.fullname}")
	private String fullname;

	private List<TitleItem> titleItems = new ArrayList<TitleItem>();

	private List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();

	private List<ScoreItem> scoreItemsOneCredit = new ArrayList<ScoreItem>();

	private Player player;

	private static final Logger LOG = LoggerFactory.getLogger(PlayerBean.class);

	@PostConstruct
	public void init() {
		player = playerService.findByFullname(fullname);
		if (player == null) {
			LOG.info("Can't find player : '" + fullname + "' in database");
		} else {
			loadTitleItems();
			loadScoreItems();
		}
	}

	private void loadScoreItems() {
		for (Game game : gameService.findAllGamesPlayedBy(player)) {
			extractScoreItemsFromGame(scoreItems, game);
		}
		ScoreItems.discoverImprovableScores(scoreItems);
	}

	private void loadTitleItems() {
		Map<Title, TitleUnlockingStrategy> strategies = titleService.findAllStrategies();
		Set<Title> allTitles = strategies.keySet();
		Set<Title> titles = new TreeSet<Title>(allTitles);
		for (Title title : titles) {
			boolean isUnlocked = player.hasUnlocked(title);
			TitleItem titleItem = new TitleItem(title, isUnlocked);
			titleItems.add(titleItem);
		}
		Collections.sort(titleItems);
	}

	public Collection<TitleItem> getTitles() {
		return titleItems;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public List<ScoreItem> getScores() {
		return scoreItems;
	}

	public List<ScoreItem> getScoresOneCredit() {
		return scoreItemsOneCredit;
	}

	private void extractScoreItemsFromGame(List<ScoreItem> scoreItems, Game game) {
		Scores scores = scoreService.findAllByGame(game);
		for (String level : Levels.list()) {
			Players.extractScoreItemsFromLevels(scoreItems, game, scores, level, fullname);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
