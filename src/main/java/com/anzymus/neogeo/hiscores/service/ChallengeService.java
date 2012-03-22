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

package com.anzymus.neogeo.hiscores.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;

import com.anzymus.neogeo.hiscores.domain.Challenge;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.exception.ChallengeNotCreatedException;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ChallengeService {

	@PersistenceContext
	EntityManager em;

	@EJB
	GameService gameService;

	public Collection<Player> findPlayersToChallenge(Player player) {
		Long playerId = player.getId();
		Query query = em.createNativeQuery(
				"SELECT * FROM PLAYER WHERE ID IN(SELECT s.PLAYER_ID FROM SCORE s WHERE s.PLAYER_ID != " + playerId
						+ " AND s.GAME_ID IN (SELECT GAME_ID FROM SCORE s WHERE s.PLAYER_ID = " + playerId
						+ ")) ORDER BY FULLNAME", Player.class);
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Challenge createChallenge(Player player1, Player player2, Game game, String title, String description)
			throws ChallengeNotCreatedException {
		if (!areChallengeable(player1, player2, game)) {
			throw new ChallengeNotCreatedException(player1.getFullname() + " can't challenge " + player2.getFullname());
		}
		Challenge challenge = new Challenge();
		challenge.setPlayer1(player1);
		challenge.setPlayer2(player2);
		challenge.setGame(game);
		challenge.setTitle(title);
		challenge.setDescription(description);
		DateTime oneMonthLater = new DateTime().plusMonths(1);
		Date finishDate = oneMonthLater.toDate();
		challenge.setFinishDate(finishDate);
		em.persist(challenge);
		return challenge;
	}

	private boolean areChallengeable(Player player1, Player player2, Game game) {
		List<Game> gamesPlayedByPlayer1 = gameService.findAllGamesPlayedBy(player1);
		if (gamesPlayedByPlayer1.isEmpty()) {
			return false;
		}
		List<Game> gamesPlayedByPlayer2 = gameService.findAllGamesPlayedBy(player2);
		if (gamesPlayedByPlayer2.isEmpty()) {
			return false;
		}
		return true;
	}

	public Collection<Challenge> findAllActiveChallenges() {
		TypedQuery<Challenge> query = em.createNamedQuery("challenge_findAllActiveChallenges", Challenge.class);
		query.setParameter("finishDate", new Date());
		return query.getResultList();
	}

	public Collection<Game> findCommonGamesFor(Player player1, Player player2) {
		List<Game> player1games = gameService.findAllGamesPlayedBy(player1);
		List<Game> player2games = gameService.findAllGamesPlayedBy(player2);
		Collection<Game> commonsGames = new TreeSet<Game>();
		for (Game game : player1games) {
			if (player2games.contains(game)) {
				commonsGames.add(game);
			}
		}
		return commonsGames;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Challenge");
		query.executeUpdate();
	}

}
