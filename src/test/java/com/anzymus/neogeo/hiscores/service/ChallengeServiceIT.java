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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Challenge;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.exception.ChallengeNotCreatedException;

public class ChallengeServiceIT extends AbstractTest {

	@Test
	public void should_get_available_one_player_to_challenge() {
		Player availablePlayer = createPlayer();
		Player player = createPlayer();
		Game game = createGame();

		createScore(player, game);
		createScore(availablePlayer, game);

		Collection<Player> players = challengeService.findPlayersToChallenge(player);

		assertTrue(players.contains(availablePlayer));
	}

	@Test
	public void should_get_three_available_players_to_challenge() {
		Player availablePlayer1 = createPlayer();
		Player availablePlayer2 = createPlayer();
		Player availablePlayer3 = createPlayer();
		Player player = createPlayer();
		Game game = createGame();

		createScore(player, game);
		createScore(availablePlayer1, game);
		createScore(availablePlayer2, game);
		createScore(availablePlayer3, game);

		Collection<Player> players = challengeService.findPlayersToChallenge(player);

		assertEquals(3, players.size());
		assertTrue(players.contains(availablePlayer1));
		assertTrue(players.contains(availablePlayer1));
		assertTrue(players.contains(availablePlayer1));
	}

	@Test
	public void should_not_get_available_players_to_challenge() {
		Player unavailablePlayer = createPlayer();
		Player player = createPlayer();
		Game game1 = createGame();
		Game game2 = createGame();

		createScore(player, game1);
		createScore(unavailablePlayer, game2);

		Collection<Player> players = challengeService.findPlayersToChallenge(player);

		assertTrue(players.isEmpty());
	}

	@Test
	public void should_create_a_challenge() throws ChallengeNotCreatedException {
		Player availablePlayer = createPlayer();
		Player player = createPlayer();
		Game game = createGame();
		createScore(player, game);
		createScore(availablePlayer, game);

		Date finishDate = new DateTime().plusMonths(1).toDate();

		Challenge challenge = challengeService.createChallenge(player, availablePlayer, game, "Challenge test",
				"Challenge description");
		assertNotNull(challenge.getId());
		assertEquals(player, challenge.getPlayer1());
		assertEquals(availablePlayer, challenge.getPlayer2());
		assertEquals(game, challenge.getGame());
		assertEquals("Challenge test", challenge.getTitle());
		assertEquals("Challenge description", challenge.getDescription());
		assertTrue(finishDate.before(challenge.getFinishDate()));
	}

	@Test(expected = ChallengeNotCreatedException.class)
	public void should_not_create_a_challenge() throws ChallengeNotCreatedException {
		Player availablePlayer = createPlayer();
		Player player = createPlayer();
		Game game = createGame();

		challengeService.createChallenge(player, availablePlayer, game, "Challenge test", "Challenge description");
	}

	@Test
	public void should_find_all_active_challenges() throws ChallengeNotCreatedException {
		Player availablePlayer = createPlayer();
		Player player = createPlayer();
		Game game = createGame();
		createScore(player, game);
		createScore(availablePlayer, game);

		Collection<Challenge> challenges = challengeService.findAllActiveChallenges();
		int initialSize = challenges.size();

		Challenge challenge = challengeService.createChallenge(player, availablePlayer, game, "Challenge test",
				"Challenge description");

		challenges = challengeService.findAllActiveChallenges();
		assertEquals(initialSize + 1, challenges.size());
		assertTrue(challenges.contains(challenge));
	}

	@Test
	public void should_have_two_games_in_common() {
		Player availablePlayer = createPlayer();
		Player player = createPlayer();
		Game game1 = createGame();
		Game game2 = createGame();
		createGame();

		createScore(player, game1);
		createScore(availablePlayer, game1);
		createScore(player, game2);
		createScore(availablePlayer, game2);

		Collection<Game> games = challengeService.findCommonGamesFor(player, availablePlayer);
		assertEquals(2, games.size());
		assertTrue(games.contains(game1));
		assertTrue(games.contains(game1));
	}

}
