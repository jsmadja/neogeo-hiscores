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

package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.anzymus.neogeo.hiscores.domain.Achievement;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class AbstractGenreTitleStrategyTest {

	AbstractGenreTitleStrategy abstractGenreTitleStrategy;

	@Before
	public void setUp() {
		abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;
	}

	@Test
	public void should_unlock_for_0_percent_accomplished_with_one_game() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores = mock(Scores.class);
		Player player = new Player();
		when(scores.getRank(player)).thenReturn(13);

		scoresByGame.add(scores);
		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(any(String.class))).thenReturn(scoresByGame);

		assertFalse(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_for_100_percent_accomplished_with_one_game() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores = mock(Scores.class);
		Player player = new Player();
		when(scores.getRank(player)).thenReturn(1);

		scoresByGame.add(scores);
		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(any(String.class))).thenReturn(scoresByGame);

		assertTrue(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_for_100_percent_accomplished_with_two_games() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		Player player = new Player();
		when(scores1.getRank(player)).thenReturn(1);
		scoresByGame.add(scores1);

		Scores scores2 = mock(Scores.class);
		when(scores2.getRank(player)).thenReturn(1);
		scoresByGame.add(scores2);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(any(String.class))).thenReturn(scoresByGame);

		assertTrue(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_for_0_percent_accomplished_with_two_games() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		Player player = new Player();
		when(scores1.getRank(player)).thenReturn(13);
		scoresByGame.add(scores1);

		Scores scores2 = mock(Scores.class);
		when(scores2.getRank(player)).thenReturn(13);
		scoresByGame.add(scores2);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		assertFalse(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_for_50_percent_accomplished_with_two_games() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		Player player = new Player();
		when(scores1.getRank(player)).thenReturn(1);
		scoresByGame.add(scores1);

		Scores scores2 = mock(Scores.class);
		when(scores2.getRank(player)).thenReturn(13);
		scoresByGame.add(scores2);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		assertTrue(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_for_66_percent_accomplished_with_two_games() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		Player player = new Player();
		when(scores1.getRank(player)).thenReturn(1);
		scoresByGame.add(scores1);

		Scores scores2 = mock(Scores.class);
		when(scores2.getRank(player)).thenReturn(13);
		scoresByGame.add(scores2);

		Scores scores3 = mock(Scores.class);
		when(scores3.getRank(player)).thenReturn(1);
		scoresByGame.add(scores3);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		assertTrue(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_for_33_percent_accomplished_with_two_games() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		Player player = new Player();
		when(scores1.getRank(player)).thenReturn(13);
		scoresByGame.add(scores1);

		Scores scores2 = mock(Scores.class);
		when(scores2.getRank(player)).thenReturn(13);
		scoresByGame.add(scores2);

		Scores scores3 = mock(Scores.class);
		when(scores3.getRank(player)).thenReturn(1);
		scoresByGame.add(scores3);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		assertFalse(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_for_50_percent_accomplished_with_one_game_and_one_game_not_played() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		Player player = new Player();
		when(scores1.getRank(player)).thenReturn(3);
		scoresByGame.add(scores1);

		Scores scores2 = mock(Scores.class);
		when(scores2.getRank(player)).thenReturn(9);
		scoresByGame.add(scores2);

		Scores scores3 = mock(Scores.class);
		when(scores3.getRank(player)).thenReturn(Integer.MAX_VALUE);
		scoresByGame.add(scores3);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		assertTrue(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_for_only_not_played_games() {
		Player player = new Player();

		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores = mock(Scores.class);
		when(scores.getRank(player)).thenReturn(Integer.MAX_VALUE);
		scoresByGame.add(scores);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		assertFalse(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_have_an_achievement_of_0_percent() {
		Player player = new Player();

		Achievement achievement = abstractGenreTitleStrategy.getAchievementFor(player);

		assertEquals(0, achievement.getProgressInPercent());
		assertEquals(1, achievement.getSteps().size());
		assertEquals("Have a rank Between 1st and 3rd place in Shooter games", achievement.getSteps().get(0).getDescription());
		assertFalse(achievement.getSteps().get(0).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_0_percent_with_one_game() {
		Player player = new Player();

		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		List<Score> scores = new ArrayList<Score>();
		Game game = new Game("View Point");
		scores.add(new Score("1", player, "MVS", game, "pictureUrl"));
		when(scores1.asSortedList()).thenReturn(scores);
		when(scores1.getRank(player)).thenReturn(4);
		scoresByGame.add(scores1);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		Achievement achievement = abstractGenreTitleStrategy.getAchievementFor(player);

		assertEquals(0, achievement.getProgressInPercent());
		assertEquals(1, achievement.getSteps().size());
		assertEquals("View Point", achievement.getSteps().get(0).getDescription());
		assertFalse(achievement.getSteps().get(0).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_100_percent() {
		Player player = new Player();

		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores1 = mock(Scores.class);
		List<Score> scores = new ArrayList<Score>();
		Game game = new Game("View Point");
		scores.add(new Score("1", player, "MVS", game, "pictureUrl"));
		when(scores1.asSortedList()).thenReturn(scores);
		when(scores1.getRank(player)).thenReturn(3);
		scoresByGame.add(scores1);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		Achievement achievement = abstractGenreTitleStrategy.getAchievementFor(player);

		assertEquals(100, achievement.getProgressInPercent());
		assertEquals(1, achievement.getSteps().size());
		assertEquals("View Point", achievement.getSteps().get(0).getDescription());
		assertTrue(achievement.getSteps().get(0).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_100_percent_on_two_games() {
		Game game1 = new Game("View Point");
		Game game2 = new Game("Last Resort");

		Player player = new Player();

		List<Score> allScores1 = new ArrayList<Score>();
		allScores1.add(new Score("1", player, "MVS", game1, "pictureUrl"));
		Scores scores1 = mock(Scores.class);
		when(scores1.asSortedList()).thenReturn(allScores1);
		when(scores1.getRank(player)).thenReturn(3);

		List<Score> allScores2 = new ArrayList<Score>();
		allScores2.add(new Score("1", player, "MVS", game2, "pictureUrl"));
		Scores scores2 = mock(Scores.class);
		when(scores2.asSortedList()).thenReturn(allScores2);
		when(scores2.getRank(player)).thenReturn(1);

		List<Scores> scoresByGame = new ArrayList<Scores>();
		scoresByGame.add(scores1);
		scoresByGame.add(scores2);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		Achievement achievement = abstractGenreTitleStrategy.getAchievementFor(player);

		assertEquals(100, achievement.getProgressInPercent());
		assertEquals(2, achievement.getSteps().size());
		assertEquals("View Point", achievement.getSteps().get(0).getDescription());
		assertTrue(achievement.getSteps().get(0).isComplete());
		assertEquals("Last Resort", achievement.getSteps().get(1).getDescription());
		assertTrue(achievement.getSteps().get(1).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_100_percent_on_three_games() {
		Game game1 = new Game("View Point");
		Game game2 = new Game("Last Resort");
		Game game3 = new Game("Pulstar");

		Player player = new Player();

		List<Score> allScores1 = new ArrayList<Score>();
		allScores1.add(new Score("1", player, "MVS", game1, "pictureUrl"));
		Scores scores1 = mock(Scores.class);
		when(scores1.asSortedList()).thenReturn(allScores1);
		when(scores1.getRank(player)).thenReturn(3);

		List<Score> allScores2 = new ArrayList<Score>();
		allScores2.add(new Score("1", player, "MVS", game2, "pictureUrl"));
		Scores scores2 = mock(Scores.class);
		when(scores2.asSortedList()).thenReturn(allScores2);
		when(scores2.getRank(player)).thenReturn(1);

		List<Score> allScores3 = new ArrayList<Score>();
		allScores3.add(new Score("1", player, "MVS", game3, "pictureUrl"));
		Scores scores3 = mock(Scores.class);
		when(scores3.asSortedList()).thenReturn(allScores3);
		when(scores3.getRank(player)).thenReturn(10);

		List<Scores> scoresByGame = new ArrayList<Scores>();
		scoresByGame.add(scores1);
		scoresByGame.add(scores2);
		scoresByGame.add(scores3);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		Achievement achievement = abstractGenreTitleStrategy.getAchievementFor(player);

		assertEquals(100, achievement.getProgressInPercent());
		assertEquals(2, achievement.getSteps().size());
		assertEquals("View Point", achievement.getSteps().get(0).getDescription());
		assertTrue(achievement.getSteps().get(0).isComplete());
		assertEquals("Last Resort", achievement.getSteps().get(1).getDescription());
		assertTrue(achievement.getSteps().get(1).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_50_percent_on_three_games() {
		Game game1 = new Game("View Point");
		Game game2 = new Game("Last Resort");
		Game game3 = new Game("Pulstar");

		Player player = new Player();

		List<Score> allScores1 = new ArrayList<Score>();
		allScores1.add(new Score("1", player, "MVS", game1, "pictureUrl"));
		Scores scores1 = mock(Scores.class);
		when(scores1.asSortedList()).thenReturn(allScores1);
		when(scores1.getRank(player)).thenReturn(3);

		List<Score> allScores2 = new ArrayList<Score>();
		allScores2.add(new Score("1", player, "MVS", game2, "pictureUrl"));
		Scores scores2 = mock(Scores.class);
		when(scores2.asSortedList()).thenReturn(allScores2);
		when(scores2.getRank(player)).thenReturn(10);

		List<Score> allScores3 = new ArrayList<Score>();
		allScores3.add(new Score("1", player, "MVS", game3, "pictureUrl"));
		Scores scores3 = mock(Scores.class);
		when(scores3.asSortedList()).thenReturn(allScores3);
		when(scores3.getRank(player)).thenReturn(10);

		List<Scores> scoresByGame = new ArrayList<Scores>();
		scoresByGame.add(scores1);
		scoresByGame.add(scores2);
		scoresByGame.add(scores3);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenre(Mockito.anyString())).thenReturn(scoresByGame);

		Achievement achievement = abstractGenreTitleStrategy.getAchievementFor(player);

		assertEquals(50, achievement.getProgressInPercent());
		assertEquals(3, achievement.getSteps().size());
		assertEquals("View Point", achievement.getSteps().get(0).getDescription());
		assertEquals("3rd", achievement.getSteps().get(0).getExtra());
		assertTrue(achievement.getSteps().get(0).isComplete());
		assertEquals("Last Resort", achievement.getSteps().get(1).getDescription());
		assertEquals("10th", achievement.getSteps().get(1).getExtra());
		assertFalse(achievement.getSteps().get(1).isComplete());
		assertEquals("Pulstar", achievement.getSteps().get(2).getDescription());
		assertEquals("10th", achievement.getSteps().get(2).getExtra());
		assertFalse(achievement.getSteps().get(2).isComplete());
	}

}
