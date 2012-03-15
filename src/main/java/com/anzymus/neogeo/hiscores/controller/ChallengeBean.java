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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.ChallengeService;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class ChallengeBean {
	public static final Logger LOG = Logger.getLogger(ChallengeBean.class.getName());

	private long currentPlayer1;
	private long currentPlayer2;

	private Collection<ChallengeableGame> challengeableGames = new ArrayList<ChallengeableGame>();
	private List<Player> player1List;
	private Collection<Player> player2List = new ArrayList<Player>();

	private Player player1;
	private Player player2;

	private String title;
	private String description;

	@EJB
	GameService gameService;

	@EJB
	PlayerService playerService;

	@EJB
	ScoreService scoreService;

	@EJB
	ChallengeService challengeService;

	FacesContext facesContext = FacesContext.getCurrentInstance();
	private Map<String, String> parameters;

	@PostConstruct
	public void init() {
		parameters = facesContext.getExternalContext().getRequestParameterMap();

		player1List = playerService.findAll();
		if (player1 == null) {
			player1 = player1List.get(0);
			currentPlayer1 = player1.getId();
			player2List = challengeService.findPlayersToChallenge(player1);
			Iterator<Player> iterator = player2List.iterator();
			if (iterator.hasNext()) {
				player2 = iterator.next();
				currentPlayer2 = player2.getId();
			}
		}
		String player1Id = parameters.get("form:player1");
		String player2Id = parameters.get("form:player2");
		if (player1Id != null) {
			currentPlayer1 = Long.valueOf(player1Id);
			player1 = playerService.findById(currentPlayer1);
			player2List = challengeService.findPlayersToChallenge(player1);
			Iterator<Player> iterator = player2List.iterator();
			if (iterator.hasNext()) {
				player2 = iterator.next();
				currentPlayer2 = player2.getId();
			}
		}
		if (player2Id != null) {
			currentPlayer2 = Long.valueOf(player2Id);
			player2 = playerService.findById(currentPlayer2);
		}
		if (player2List.size() > 0) {
			Collection<Game> commonGames = challengeService.findCommonGamesFor(player1, player2);
			toChallengeableGames(commonGames);
		}
	}

	public void challengerListener(AjaxBehaviorEvent event) {
		LOG.info(player2List.size() + " players to challenge");
	}

	public void gameListener(AjaxBehaviorEvent event) {
		LOG.info(challengeableGames.size() + " games to challenge");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public long getCurrentPlayer1() {
		return currentPlayer1;
	}

	public void setCurrentPlayer1(long currentPlayer1) {
		this.currentPlayer1 = currentPlayer1;
	}

	public long getCurrentPlayer2() {
		return currentPlayer2;
	}

	public void setCurrentPlayer2(long currentPlayer2) {
		this.currentPlayer2 = currentPlayer2;
	}

	public Collection<ChallengeableGame> getChallengeableGames() {
		return challengeableGames;
	}

	public void setChallengeableGames(Collection<ChallengeableGame> challengeableGames) {
		this.challengeableGames = challengeableGames;
	}

	public List<Player> getPlayer1List() {
		return player1List;
	}

	public void setPlayer1List(List<Player> player1List) {
		this.player1List = player1List;
	}

	public Collection<Player> getPlayer2List() {
		return player2List;
	}

	public void setPlayer2List(Collection<Player> player2List) {
		this.player2List = player2List;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public static Logger getLog() {
		return LOG;
	}

	private void toChallengeableGames(Collection<Game> games) {
		Scores player1Scores = scoreService.findAllByPlayer(player1);
		Scores player2Scores = scoreService.findAllByPlayer(player2);
		for (Game game : games) {
			this.challengeableGames.add(toChallengeableGame(game, player1Scores, player2Scores));
		}
	}

	private ChallengeableGame toChallengeableGame(Game game, Scores player1Scores, Scores player2Scores) {
		ChallengeableGame challengeableGame = new ChallengeableGame(game);

		Score player1Score = player1Scores.getBestScoreFor(player1, "MVS", game);
		Score player2Score = player2Scores.getBestScoreFor(player2, "MVS", game);

		challengeableGame.setPlayer1Score(player1Score);
		challengeableGame.setPlayer2Score(player2Score);

		return challengeableGame;
	}

}
