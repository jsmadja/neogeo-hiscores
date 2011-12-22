package com.anzymus.neogeo.hiscores.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class PlayersTest {

	@Test
	public void should_discover_improvable_score() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", new Game(), ""));
		scoreItem1.setNegativeGap("3");
		ScoreItem scoreItem2 = new ScoreItem(new Score("456", player, "MVS", new Game(), ""));
		scoreItem2.setNegativeGap("2");

		List<ScoreItem> scoreItems = Arrays.asList(scoreItem1, scoreItem2);
		Players.discoverImprovableScores(scoreItems);

		assertTrue(scoreItem2.isImprovable());
		assertFalse(scoreItem1.isImprovable());
	}

	@Test
	public void should_not_discover_improvable_soccer_score() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("1-2-3", player, "MVS", new Game(), ""));
		ScoreItem scoreItem2 = new ScoreItem(new Score("4-5-6", player, "MVS", new Game(), ""));

		List<ScoreItem> scoreItems = Arrays.asList(scoreItem1, scoreItem2);
		Players.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
		assertFalse(scoreItem2.isImprovable());
	}

	@Test
	public void should_not_discover_improvable_chrono_score() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("1:2:3", player, "MVS", new Game(), ""));
		ScoreItem scoreItem2 = new ScoreItem(new Score("4:5:6", player, "MVS", new Game(), ""));

		List<ScoreItem> scoreItems = Arrays.asList(scoreItem1, scoreItem2);
		Players.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
		assertFalse(scoreItem2.isImprovable());
	}

	@Test
	public void should_not_set_improvable_when_there_is_no_gap() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", new Game(), ""));
		scoreItem1.setNegativeGap("");

		List<ScoreItem> scoreItems = Arrays.asList(scoreItem1);
		Players.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
	}

	@Test
	public void should_not_set_improvable_when_there_is_a_null_gap() {
		Player player = new Player("fullname");
		ScoreItem scoreItem1 = new ScoreItem(new Score("123", player, "MVS", new Game(), ""));
		scoreItem1.setNegativeGap(null);

		List<ScoreItem> scoreItems = Arrays.asList(scoreItem1);
		Players.discoverImprovableScores(scoreItems);

		assertFalse(scoreItem1.isImprovable());
	}
}
