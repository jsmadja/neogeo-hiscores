package com.anzymus.neogeo.hiscores.success;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

@RunWith(MockitoJUnitRunner.class)
public class ShmuperTitleStrategyTest {

	TitleUnlockingStrategy titleUnlockingStrategy = new ShmuperTitleStrategy();

	@Mock
	TitleService titleService;

	@Before
	public void init() {
		titleUnlockingStrategy.initialize(titleService);
	}

	@Test
	public void should_detect_unlocking() {
		Player player = new Player("fullname");

		// when(titleService.getNumScoredGamesByGenres(player, "Shooter")).thenReturn(3L);
		//
		// boolean unlocked = titleUnlockingStrategy.isUnlockable(player);
		//
		// assertTrue(unlocked);
		// verify(titleService).getNumScoredGamesByGenres(player, "Shooter");
	}

}
