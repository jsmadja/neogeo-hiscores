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

        boolean unlocked = titleUnlockingStrategy.isUnlockable(player);

        assertTrue(unlocked);
    }

    @Test
    public void should_not_detect_unlocking() {
        when(titleService.getNumScoresByPlayer(player)).thenReturn(0L);

        boolean unlocked = titleUnlockingStrategy.isUnlockable(player);

        assertFalse(unlocked);
    }

    protected abstract long getNumScoresToCreate();

    protected abstract TitleUnlockingStrategy getMegaShockStrategy();

}
