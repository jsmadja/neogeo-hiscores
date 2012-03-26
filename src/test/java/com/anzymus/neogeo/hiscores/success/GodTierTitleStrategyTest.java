package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class GodTierTitleStrategyTest {

	AbstractTitleStrategy godTierStrategy;
	Player player;

	@Before
	public void setUp() {
		godTierStrategy = new GodTierTitleStrategy();
		player = new Player();
		godTierStrategy.titleService = mock(TitleService.class);
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_10() {
		when(godTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(10.0D);

		assertTrue(godTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_not_equal_to_10() {
		when(godTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(9.0D);

		assertFalse(godTierStrategy.isUnlockable(player));
	}
}
