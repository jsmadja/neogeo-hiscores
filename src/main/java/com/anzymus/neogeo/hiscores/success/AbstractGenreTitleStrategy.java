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

import java.util.List;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Scores;

public abstract class AbstractGenreTitleStrategy extends AbstractTitleStrategy {

	@Override
	public boolean isUnlockable(Player player) {
		List<Scores> scoresByGame = titleService.getScoresByGameGenres(getGenres());
		if (scoresByGame.size() < 3) {
			return false;
		}
		for (Scores scores : scoresByGame) {
			int rank = scores.getRank(player);
			if (rank > 3) {
				return false;
			}
		}
		return true;
	}

	protected abstract String[] getGenres();

}
