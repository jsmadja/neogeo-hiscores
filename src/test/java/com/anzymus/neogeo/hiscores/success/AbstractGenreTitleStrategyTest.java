package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class AbstractGenreTitleStrategyTest {

	@Test
	public void should_unlock_for_0_percent_accomplished_with_one_game() {
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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
		AbstractGenreTitleStrategy abstractGenreTitleStrategy = new ShooterTitleStrategy();
		TitleService titleService = mock(TitleService.class);
		abstractGenreTitleStrategy.titleService = titleService;

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

}
