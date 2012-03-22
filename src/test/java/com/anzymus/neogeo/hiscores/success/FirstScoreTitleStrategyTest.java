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

public class FirstScoreTitleStrategyTest {

    @Mock
    TitleService titleService;

    TitleUnlockingStrategy firstScoreTitle;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        firstScoreTitle = new FirstScoreTitleStrategy();
        firstScoreTitle.initialize(titleService);
    }

    @Test
    public void should_detect_unlocked_title() {
        Player player = new Player();

        when(titleService.getNumScoresByPlayer(player)).thenReturn(1L);

        boolean unlocked = firstScoreTitle.isUnlockable(player);
        assertTrue(unlocked);
    }

    @Test
    public void should_not_detect_unlocked_title() {
        Player player = new Player();

        when(titleService.getNumScoresByPlayer(player)).thenReturn(0L);

        boolean unlocked = firstScoreTitle.isUnlockable(player);
        assertFalse(unlocked);
    }

}
