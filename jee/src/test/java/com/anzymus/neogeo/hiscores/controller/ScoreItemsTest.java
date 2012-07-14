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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreItemsTest {

	@Test
	public void should_discover_improvable_score() {
		Player player = new Player("fullname");
		Game game = new Game();
		game.setImprovable(true);
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", game, ""));
		scoreItem1.setNegativeGap("3");
		ScoreItem scoreItem2 = new ScoreItem(new Score("456", player, "MVS", game, ""));
		scoreItem2.setNegativeGap("2");

		List<ScoreItem> scoreItems = asList(scoreItem1, scoreItem2);
		ScoreItems.discoverImprovableScores(scoreItems);

		assertTrue(scoreItem2.isImprovable());
		assertFalse(scoreItem1.isImprovable());
	}

	@Test
	public void should_not_discover_improvable_soccer_score() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("1-2-3", player, "MVS", new Game(), ""));
		ScoreItem scoreItem2 = new ScoreItem(new Score("4-5-6", player, "MVS", new Game(), ""));

		List<ScoreItem> scoreItems = asList(scoreItem1, scoreItem2);
		ScoreItems.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
		assertFalse(scoreItem2.isImprovable());
	}

	@Test
	public void should_not_discover_improvable_chrono_score() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("1:2:3", player, "MVS", new Game(), ""));
		ScoreItem scoreItem2 = new ScoreItem(new Score("4:5:6", player, "MVS", new Game(), ""));

		List<ScoreItem> scoreItems = asList(scoreItem1, scoreItem2);
		ScoreItems.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
		assertFalse(scoreItem2.isImprovable());
	}

	@Test
	public void should_not_set_improvable_when_there_is_no_gap() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", new Game(), ""));
		scoreItem1.setNegativeGap("");

		List<ScoreItem> scoreItems = asList(scoreItem1);
		ScoreItems.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
	}

	@Test
	public void should_not_set_improvable_when_there_is_a_null_gap() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", new Game(), ""));
		scoreItem1.setNegativeGap(null);

		List<ScoreItem> scoreItems = asList(scoreItem1);
		ScoreItems.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
	}

	@Test
	public void should_not_set_improvable_when_game_is_not_improvable() {
		Player player = new Player("fullname");
		Game game = new Game();
		game.setImprovable(false);
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", game, ""));
		scoreItem1.setNegativeGap("3");

		List<ScoreItem> scoreItems = asList(scoreItem1);
		ScoreItems.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
	}
}
