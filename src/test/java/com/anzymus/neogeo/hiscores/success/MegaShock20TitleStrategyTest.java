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

import com.anzymus.neogeo.hiscores.domain.Achievement;
import com.anzymus.neogeo.hiscores.domain.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class MegaShock20TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock20TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 20;
    }

    @Test
    public void should_have_an_achievement_of_50_percent() {
        Player player = new Player();
        when(titleService.getNumScoresByPlayer(player)).thenReturn(10L);

        Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

        assertEquals(50, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Add 20 scores in neogeo-hiscores.com", achievement.getSteps().get(0).getDescription());
        assertFalse(achievement.getSteps().get(0).isComplete());
    }

}
