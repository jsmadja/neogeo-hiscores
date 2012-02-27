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
