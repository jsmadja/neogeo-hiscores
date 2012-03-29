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

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Player;

public class AllClearerTitleStrategyTest extends AbstractAllClearerTitleStrategyTest {

	@Override
	protected TitleUnlockingStrategy getAllClearStrategy() {
		return new AllClearerTitleStrategy();
	}

	@Override
	protected int getNumAllClearsToCreate() {
		return 1;
	}

	@Test
	public void should_have_an_achievement_of_0_percent() {
		Player player = new Player();

		Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

		assertEquals(0, achievement.getProgressInPercent());
		assertEquals(1, achievement.getSteps().size());
		assertEquals("Finish 1 game with only one credit", achievement.getSteps().get(0).getDescription());
		assertFalse(achievement.getSteps().get(0).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_100_percent() {
		Player player = new Player();
		when(titleService.getNumAllClearsByPlayer(player)).thenReturn(1);

		Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

		assertEquals(100, achievement.getProgressInPercent());
		assertEquals(1, achievement.getSteps().size());
		assertEquals("Finish 1 game with only one credit", achievement.getSteps().get(0).getDescription());
		assertTrue(achievement.getSteps().get(0).isComplete());
	}

	@Test
	public void should_have_an_achievement_of_100_percent_with_more_than_one_score() {
		Player player = new Player();
		when(titleService.getNumAllClearsByPlayer(player)).thenReturn(5);

		Achievement achievement = titleUnlockingStrategy.getAchievementFor(player);

		assertEquals(100, achievement.getProgressInPercent());
		assertEquals(1, achievement.getSteps().size());
		assertEquals("Finish 1 game with only one credit", achievement.getSteps().get(0).getDescription());
		assertTrue(achievement.getSteps().get(0).isComplete());
	}

}
