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

package com.anzymus.neogeo.hiscores.service;

import static java.text.MessageFormat.format;

import com.anzymus.neogeo.hiscores.common.IntegerToRank;

public class ChangesService {

	private String lastValues = "";
	private String currentValues = "";

	public String getChanges(String player) {
		String[] currentValuesAsArray = currentValues.split("\n");
		String[] lastValuesAsArray = lastValues.split("\n");
		String changes = "";
		for (int i = 0; i < currentValuesAsArray.length; i++) {
			String playerName = currentValuesAsArray[i].split(";")[0];
			String oldPlayerName = "";
			if (i < lastValuesAsArray.length) {
				oldPlayerName = lastValuesAsArray[i].split(";")[0];
			}
			boolean hasChanged = !oldPlayerName.equals(playerName);
			if (hasChanged) {
				if (player.equals(playerName)) {
					String rank = IntegerToRank.getOrdinalFor(i + 1);
					changes += format("{0} is now at the {1} place!", player, rank);
				}
			}
		}
		return changes;
	}

	public void setLastValues(String lastValues) {
		this.lastValues = lastValues;
	}

	public void setCurrentValues(String currentValues) {
		this.currentValues = currentValues;
	}

}
