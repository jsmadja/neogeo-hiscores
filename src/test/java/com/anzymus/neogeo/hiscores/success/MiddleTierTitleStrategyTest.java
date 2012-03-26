package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class MiddleTierTitleStrategyTest {

	AbstractTitleStrategy middleTierStrategy;
	Player player;

	@Before
	public void setUp() {
		middleTierStrategy = new MiddleTierTitleStrategy();
		player = new Player();
		middleTierStrategy.titleService = mock(TitleService.class);
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_7() {
		when(middleTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(7.0D);

		assertTrue(middleTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_6_dot_5() {
		when(middleTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(6.5D);

		assertTrue(middleTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_6() {
		when(middleTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(6.0D);

		assertTrue(middleTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_5() {
		when(middleTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(5.0D);

		assertTrue(middleTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_4() {
		when(middleTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(4.0D);

		assertTrue(middleTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_equal_to_3() {
		when(middleTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(3.0D);

		assertFalse(middleTierStrategy.isUnlockable(player));
	}

}
