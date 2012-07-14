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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.anzymus.neogeo.hiscores.domain.Achievement;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class WorldHeroTitleStrategyTest {

    @Mock
    TitleService titleService;

    TitleUnlockingStrategy worldHeroTitleStrategy;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        worldHeroTitleStrategy = new WorldHeroTitleStrategy();
        worldHeroTitleStrategy.initialize(titleService);
    }

    @Test
    public void should_detect_unlocked_title() {
        Player player = new Player();

        when(titleService.hasScoreInGame(player, "World Heroes (Normal)")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes (Death Match)")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes 2 (Death Match)")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes 2 (Normal Game)")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes 2 Jet")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes Perfect")).thenReturn(true);

        boolean unlocked = worldHeroTitleStrategy.isUnlockable(player);
        assertTrue(unlocked);
    }

    @Test
    public void should_not_detect_unlocked_title() {
        Player player = new Player();
        boolean unlocked = worldHeroTitleStrategy.isUnlockable(player);
        assertFalse(unlocked);
    }

    @Test
    public void should_have_an_achievement_of_0_percent() {
        Player player = new Player();

        when(titleService.hasScoreInGame(player, "World Heroes (Normal)")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes (Death Match)")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes 2 (Death Match)")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes 2 (Normal Game)")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes 2 Jet")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes Perfect")).thenReturn(false);

        Achievement achievement = worldHeroTitleStrategy.getAchievementFor(player);

        assertEquals(0, achievement.getProgressInPercent());
        assertEquals(6, achievement.getSteps().size());
        assertEquals("Add a score for World Heroes (Normal)", achievement.getSteps().get(0).getDescription());
        assertEquals("Add a score for World Heroes (Death Match)", achievement.getSteps().get(1).getDescription());

        for (int i = 0; i < 6; i++) {
            assertFalse(achievement.getSteps().get(i).isComplete());
        }
    }

    @Test
    public void should_have_an_achievement_of_33_percent() {
        Player player = new Player();

        when(titleService.hasScoreInGame(player, "World Heroes (Normal)")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes (Death Match)")).thenReturn(true);
        when(titleService.hasScoreInGame(player, "World Heroes 2 (Death Match)")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes 2 (Normal Game)")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes 2 Jet")).thenReturn(false);
        when(titleService.hasScoreInGame(player, "World Heroes Perfect")).thenReturn(false);

        Achievement achievement = worldHeroTitleStrategy.getAchievementFor(player);

        assertEquals(33, achievement.getProgressInPercent());
        assertEquals(6, achievement.getSteps().size());
        assertTrue(achievement.getSteps().get(0).isComplete());
        assertTrue(achievement.getSteps().get(1).isComplete());
        for (int i = 2; i < 6; i++) {
            assertFalse(achievement.getSteps().get(i).isComplete());
        }
    }
}
