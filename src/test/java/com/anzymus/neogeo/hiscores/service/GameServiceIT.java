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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class GameServiceIT extends AbstractTest {
	@Test
	public void should_add_new_game() {
		Game game = new Game("Fatal Fury");

		gameService.store(game);
		List<Game> games = gameService.findAll();
		assertTrue(games.contains(game));
	}

	@Test
	public void should_retrieve_one_played_game() {
		Game game = new Game("Fatal Fury 2");
		gameService.store(game);

		Player player = new Player("player");
		playerService.store(player);

		Score score = new Score("1", player, "MVS", game, "http://");
		scoreService.store(score);

		List<Game> games = gameService.findAllGamesPlayedBy(player);
		assertEquals(1, games.size());

		assertEquals("Fatal Fury 2", games.get(0).getName());
	}

	@Test
	public void should_retrieve_zero_game() {
		Game game = new Game("Fatal Fury 3");
		gameService.store(game);

		Player player1 = new Player("player2");
		playerService.store(player1);
		Player player2 = new Player("player3");
		playerService.store(player2);

		Score score = new Score("1", player1, "MVS", game, "http://");
		scoreService.store(score);

		List<Game> games = gameService.findAllGamesPlayedBy(player2);
		assertTrue(games.isEmpty());
	}

	@Ignore
	@Test
	public void should_retrieve_score_count() {
		Game game1 = new Game("Fatal Fury 4");
		game1 = gameService.store(game1);
		Game game2 = new Game("Fatal Fury 5");
		gameService.store(game2);

		Player player = new Player("player4");
		playerService.store(player);

		Score score1 = new Score("1", player, "MVS", game1, "http://");
		scoreService.store(score1);
		Score score2 = new Score("1", player, "MVS", game2, "http://");
		scoreService.store(score2);
		Score score3 = new Score("3", player, "MVS", game2, "http://");
		scoreService.store(score3);

		List<Object[]> scoreCounts = gameService.findAllScoreCountForEachGames();
		Object[] scoreCount = scoreCounts.get(scoreCounts.size() - 1);
		Long gameId = (Long) scoreCount[0];
		String gameName = (String) scoreCount[1];
		Long count = (Long) scoreCount[2];

		assertEquals(game2.getId(), gameId);
		assertEquals("Fatal Fury 5", gameName);
		assertEquals(2L, count.longValue());
	}

	@Test
	public void should_find_all_played_games() {
		int initialCount = gameService.findAllPlayedGames().size();

		Player player = createPlayer();
		Game game = createGame();
		createScore(player, game);

		List<Game> playedGames = gameService.findAllPlayedGames();

		assertEquals(initialCount + 1, playedGames.size());
	}

	@Test
	public void should_find_all_games_played_by_player() {
		Player player = createPlayer();
		Game game = createGame();
		createScore(player, game);

		List<Game> playedGames = gameService.findAllGamesPlayedBy(player);

		Game playedGame = playedGames.get(0);

		assertEquals(game, playedGame);
	}

	@Test
	public void should_get_number_of_games() {
		long expected = gameService.findAll().size();
		long actual = gameService.getNumberOfGames();
		assertEquals(expected, actual);
	}

	@Test
	public void should_get_all_unplayed_games() {
		List<Game> unplayedGames = gameService.findAllUnplayedGames();
		int initialUnplayedGames = unplayedGames.size();

		Game game1 = createGame();
		Game game2 = createGame();

		unplayedGames = gameService.findAllUnplayedGames();

		assertEquals(initialUnplayedGames + 2, unplayedGames.size());
		assertTrue(unplayedGames.contains(game1));
		assertTrue(unplayedGames.contains(game2));
	}
}
