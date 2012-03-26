package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class TopTierTitleStrategyTest {

	AbstractTitleStrategy topTierStrategy;
	Player player;

	@Before
	public void setUp() {
		topTierStrategy = new TopTierTitleStrategy();
		player = new Player();
		topTierStrategy.titleService = mock(TitleService.class);
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_9() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(9.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_8() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(8.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_7() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(7.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_10() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(10.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_equal_to_6() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(6.0D);

		assertFalse(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_equal_to_6_dot_5() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(6.5D);

		assertFalse(topTierStrategy.isUnlockable(player));
	}
}
