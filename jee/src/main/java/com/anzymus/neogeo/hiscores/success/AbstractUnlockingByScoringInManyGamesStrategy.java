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

import java.util.ArrayList;
import java.util.List;

import com.anzymus.neogeo.hiscores.domain.Achievement;
import com.anzymus.neogeo.hiscores.domain.Player;

public abstract class AbstractUnlockingByScoringInManyGamesStrategy extends AbstractTitleStrategy {

	@Override
	public boolean isUnlockable(Player player) {
		List<String> gameNames = getGameNamesToScore();
		for (String gameName : gameNames) {
			if (!titleService.hasScoreInGame(player, gameName)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Achievement getAchievementFor(Player player) {
		int completedGamesCount = 0;
		List<Step> steps = new ArrayList<Step>();
		List<String> gameNames = getGameNamesToScore();
		for (String gameName : gameNames) {
			boolean completed = titleService.hasScoreInGame(player, gameName);
			if (completed) {
				completedGamesCount++;
			}
			steps.add(new Step("Add a score for " + gameName, completed));
		}
		Achievement achievement = new Achievement(title, percent(completedGamesCount, gameNames.size()));
		for (Step step : steps) {
			achievement.addStep(step);
		}
		return achievement;
	}

	abstract List<String> getGameNamesToScore();
}
