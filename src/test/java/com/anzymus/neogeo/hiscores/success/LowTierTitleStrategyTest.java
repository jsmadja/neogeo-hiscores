package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class LowTierTitleStrategyTest {

	AbstractTitleStrategy lowTierStrategy;
	Player player;

	@Before
	public void setUp() {
		lowTierStrategy = new LowTierTitleStrategy();
		player = new Player();
		lowTierStrategy.titleService = mock(TitleService.class);
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_4() {
		when(lowTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(4.0D);

		assertTrue(lowTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_3_dot_5() {
		when(lowTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(3.5D);

		assertTrue(lowTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_3() {
		when(lowTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(3.0D);

		assertTrue(lowTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_2() {
		when(lowTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(2.0D);

		assertTrue(lowTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_1() {
		when(lowTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(1.0D);

		assertTrue(lowTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_equal_to_0() {
		when(lowTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(0.0D);

		assertFalse(lowTierStrategy.isUnlockable(player));
	}

}
