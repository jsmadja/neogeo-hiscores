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

public abstract class AbstractMinimumScoreTitleStrategy extends AbstractTitleStrategy {

	@Override
	public boolean isUnlockable(Player player) {
		return titleService.getNumScoresByPlayer(player) >= getNumScoresToUnlock();
	}

	@Override
	public Achievement getAchievementFor(Player player) {
		long numScores = titleService.getNumScoresByPlayer(player);
		if (numScores >= getNumScoresToUnlock()) {
			numScores = getNumScoresToUnlock();
		}
		Achievement achievement = new Achievement(title, percent(numScores, getNumScoresToUnlock()));
		achievement.addStep(new Step(getStepName(), numScores >= getNumScoresToUnlock()));
		return achievement;
	}

	protected abstract long getNumScoresToUnlock();

	protected String getStepName() {
		long numScores = getNumScoresToUnlock();
		return "Add " + numScores + " score" + (numScores > 1 ? "s" : "") + " in neogeo-hiscores.com";
	}
}
