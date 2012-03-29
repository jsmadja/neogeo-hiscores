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

import com.anzymus.neogeo.hiscores.domain.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class MegaShock10TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock10TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 10;
    }

    @Test
    public void should_have_an_achievement_of_0_percent() {
        Player player = new Player();

        Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

        assertEquals(0, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Add 10 scores in neogeo-hiscores.com", achievement.getSteps().get(0).getDescription());
        assertFalse(achievement.getSteps().get(0).isComplete());
    }

    @Test
    public void should_have_an_achievement_of_100_percent() {
        Player player = new Player();
        when(titleService.getNumScoresByPlayer(player)).thenReturn(10L);

        Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

        assertEquals(100, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Add 10 scores in neogeo-hiscores.com", achievement.getSteps().get(0).getDescription());
        assertTrue(achievement.getSteps().get(0).isComplete());
    }

    @Test
    public void should_have_an_achievement_of_50_percent() {
        Player player = new Player();
        when(titleService.getNumScoresByPlayer(player)).thenReturn(5L);

        Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

        assertEquals(50, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Add 10 scores in neogeo-hiscores.com", achievement.getSteps().get(0).getDescription());
        assertFalse(achievement.getSteps().get(0).isComplete());
    }

    @Test
    public void should_compute_percent() {
        MegaShock10TitleStrategy strategy = new MegaShock10TitleStrategy();
        assertEquals(0, strategy.percent(0,1));
        assertEquals(100, strategy.percent(1,1));
        assertEquals(100, strategy.percent(2,2));
        assertEquals(50, strategy.percent(1,2));
        assertEquals(33, strategy.percent(1,3));
    }

}
