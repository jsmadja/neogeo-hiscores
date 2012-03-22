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

package com.anzymus.neogeo.hiscores.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class ScoresTest {

	@Test
	public void should_add_two_scores() {
		Player player1 = new Player("abc");
		String level1 = "MVS";
		Game game1 = new Game("game");
		String pictureUrl1 = "pictureUrl";

		Score score1 = new Score("1", player1, level1, game1, pictureUrl1);

		Player player2 = new Player("def");
		String level2 = "MVS";
		Game game2 = new Game("game");
		String pictureUrl2 = "pictureUrl";

		Score score2 = new Score("1", player2, level2, game2, pictureUrl2);

		Scores scores = new Scores();
		scores.add(score1);
		scores.add(score2);

		assertEquals(2, scores.count());
	}

	@Test
	public void should_add_one_score() {
		Player player1 = new Player("abc");
		String level1 = "MVS";
		Game game1 = new Game("game");
		String pictureUrl1 = "pictureUrl";

		Score score1 = new Score("1", player1, level1, game1, pictureUrl1);

		Player player2 = new Player("abc");
		String level2 = "MVS";
		Game game2 = new Game("game");
		String pictureUrl2 = "pictureUrl";

		Score score2 = new Score("1", player2, level2, game2, pictureUrl2);

		Scores scores = new Scores();
		scores.add(score1);
		scores.add(score2);

		assertEquals(1, scores.count());
	}

	@Test
	public void should_return_empty_list_when_there_is_no_scores() {
		Scores scores = new Scores();
		List<Score> sortedScores = scores.sortByDateDesc();
		assertTrue(sortedScores.isEmpty());
	}

	@Test
	public void should_return_one_list_item_when_there_is_one_score() {
		Player player = new Player("fullname");
		String level = "MVS";
		Game game = new Game("Fatal Fury");
		String pictureUrl = "url";
		Score score = new Score("1", player, level, game, pictureUrl);

		Scores scores = new Scores();
		scores.add(score);

		List<Score> sortedScores = scores.sortByDateDesc();

		assertEquals(1, sortedScores.size());
	}

	@Test
	public void should_return_one_list_item_when_adding_two_scores_with_same_game_same_level_same_player()
			throws InterruptedException {
		Player player = new Player("fullname");
		String level = "MVS";
		Game game = new Game("Fatal Fury");
		String pictureUrl = "url";
		Score score1 = new Score("1", player, level, game, pictureUrl);
		Thread.sleep(100);
		Score score2 = new Score("2", player, level, game, pictureUrl);

		Scores scores = new Scores();
		scores.add(score1);
		scores.add(score2);

		assertEquals(1, scores.count());
		assertTrue(scores.contains(score2));
	}

	@Test
	public void should_keep_only_best_scores_for_each_player() {
		Player player = new Player("fullname");
		String level = "MVS";
		Game game = new Game("Fatal Fury");
		String pictureUrl = "url";

		Score score1 = new Score("1", player, level, game, pictureUrl);
		Score score2 = new Score("2", player, level, game, pictureUrl);

		Scores scores = new Scores();
		scores.add(score1);
		scores.add(score2);

		assertEquals(1, scores.count());
		Score score = scores.iterator().next();
		assertEquals("2", score.getValue());
	}

	@Test
	public void should_keep_only_best_scores_for_each_game() {
		Player player = new Player("fullname");
		String level = "MVS";
		Game game1 = new Game("Fatal Fury");
		Game game2 = new Game("Fatal Fury 2");
		String pictureUrl = "url";

		Score score1 = new Score("1", player, level, game1, pictureUrl);
		Score score2 = new Score("1", player, level, game2, pictureUrl);

		Scores scores = new Scores();
		scores.add(score1);
		scores.add(score2);

		assertEquals(2, scores.count());
	}

	@Test
	public void should_get_rank() {
		Player player = new Player("fullname");
		String level = "MVS";
		Game game = new Game("Fatal Fury");
		String pictureUrl = "url";

		Scores scores = new Scores();
		Score score = new Score("1", player, level, game, pictureUrl);
		scores.add(score);

		int rank = scores.getRank(player);
		assertEquals(1, rank);
	}

	@Test
	public void should_get_rank_with_two_players() {
		Player player1 = new Player("fullname1");
		Player player2 = new Player("fullname2");
		String level = "MVS";
		Game game = new Game("Fatal Fury");
		String pictureUrl = "url";

		Scores scores = new Scores();
		Score score1 = new Score("1", player1, level, game, pictureUrl);
		Score score2 = new Score("0", player2, level, game, pictureUrl);
		scores.add(score1);
		scores.add(score2);

		int rank = scores.getRank(player1);
		assertEquals(1, rank);
		rank = scores.getRank(player2);
		assertEquals(2, rank);
	}

	@Test
	public void should_get_rank_with_two_players_only_MVS() {
		Player player1 = new Player("fullname1");
		Player player2 = new Player("fullname2");
		Player player3 = new Player("fullname2");
		String level = "MVS";
		Game game = new Game("Fatal Fury");
		String pictureUrl = "url";

		Scores scores = new Scores();
		Score score1 = new Score("1", player1, level, game, pictureUrl);
		Score score2 = new Score("0", player2, level, game, pictureUrl);
		Score score3 = new Score("3", player3, "Easy", game, pictureUrl);
		scores.add(score1);
		scores.add(score2);
		scores.add(score3);

		int rank = scores.getRank(player1);
		assertEquals(1, rank);
		rank = scores.getRank(player2);
		assertEquals(2, rank);
	}

}
