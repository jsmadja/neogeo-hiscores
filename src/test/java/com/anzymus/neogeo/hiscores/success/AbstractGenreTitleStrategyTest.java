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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
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
		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

		assertFalse(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_for_100_percent_accomplished_with_one_game() {
		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores = mock(Scores.class);
		Player player = new Player();
		when(scores.getRank(player)).thenReturn(1);

		scoresByGame.add(scores);
		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

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

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

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

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

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

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

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

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

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

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

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

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

		assertTrue(abstractGenreTitleStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_for_only_not_played_games() {
		Player player = new Player();

		List<Scores> scoresByGame = new ArrayList<Scores>();
		Scores scores = mock(Scores.class);
		when(scores.getRank(player)).thenReturn(Integer.MAX_VALUE);
		scoresByGame.add(scores);

		when(abstractGenreTitleStrategy.titleService.getScoresByGameGenres(any(String[].class))).thenReturn(
				scoresByGame);

		assertFalse(abstractGenreTitleStrategy.isUnlockable(player));
	}

}
