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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.TitleService;

public class TopTierTitleStrategyTest {

	AbstractTitleStrategy topTierStrategy;
	Player player;

	@Before
	public void setUp() {
		topTierStrategy = new TopTierTitleStrategy();
		player = new Player();
		topTierStrategy.titleService = mock(TitleService.class);
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_9() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(9.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_8() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(8.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_7() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(7.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_unlock_title_for_player_with_score_equal_to_10() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(10.0D);

		assertTrue(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_equal_to_6() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(6.0D);

		assertFalse(topTierStrategy.isUnlockable(player));
	}

	@Test
	public void should_not_unlock_title_for_player_with_score_equal_to_6_dot_5() {
		when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(6.5D);

		assertFalse(topTierStrategy.isUnlockable(player));
	}

        @Test
    public void should_have_an_achievement_of_100_percent() {
        Player player = new Player();

        when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(7.0D);

        Achievement achievement = topTierStrategy.getAchievementFor(player);

        assertEquals(100, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Have an average score greater or equal to 7", achievement.getSteps().get(0).getDescription());
        assertTrue(achievement.getSteps().get(0).isComplete());
    }

    @Test
    public void should_have_an_achievement_of_100_percent_with_score_of_10() {
        Player player = new Player();

        when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(10.0D);

        Achievement achievement = topTierStrategy.getAchievementFor(player);

        assertEquals(100, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Have an average score greater or equal to 7", achievement.getSteps().get(0).getDescription());
        assertTrue(achievement.getSteps().get(0).isComplete());
    }

    @Test
    public void should_have_an_achievement_of_50_percent() {
        Player player = new Player();

        when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(3.5D);

        Achievement achievement = topTierStrategy.getAchievementFor(player);

        assertEquals(50, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Have an average score greater or equal to 7", achievement.getSteps().get(0).getDescription());
        assertFalse(achievement.getSteps().get(0).isComplete());
    }

    @Test
    public void should_have_an_achievement_of_0_percent() {
        Player player = new Player();

        when(topTierStrategy.titleService.getAverageScoreFor(player)).thenReturn(0.0D);

        Achievement achievement = topTierStrategy.getAchievementFor(player);

        assertEquals(0, achievement.getProgressInPercent());
        assertEquals(1, achievement.getSteps().size());
        assertEquals("Have an average score greater or equal to 7", achievement.getSteps().get(0).getDescription());
        assertFalse(achievement.getSteps().get(0).isComplete());
    }
}
