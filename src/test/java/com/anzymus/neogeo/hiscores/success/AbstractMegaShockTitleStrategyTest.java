package com.anzymus.neogeo.hiscores.success;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public abstract class AbstractMegaShockTitleStrategyTest {

    TitleUnlockingStrategy titleUnlockingStrategy = getMegaShockStrategy();

    @Mock
    TitleService titleService;

    Player player;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        titleUnlockingStrategy.initialize(titleService);
        player = new Player();
    }

    @Test
    public void should_detect_unlocking() {
        when(titleService.getNumScoresByPlayer(player)).thenReturn(getNumScoresToCreate());

        boolean unlocked = titleUnlockingStrategy.isUnlocked(player);

        assertTrue(unlocked);
    }

    @Test
    public void should_not_detect_unlocking() {
        when(titleService.getNumScoresByPlayer(player)).thenReturn(0L);

        boolean unlocked = titleUnlockingStrategy.isUnlocked(player);

        assertFalse(unlocked);
    }

    protected abstract long getNumScoresToCreate();

    protected abstract TitleUnlockingStrategy getMegaShockStrategy();

}
