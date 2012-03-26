package com.anzymus.neogeo.hiscores.success;

import com.anzymus.neogeo.hiscores.domain.Player;

public class MiddleTierTitleStrategy extends AbstractTitleStrategy {

	@Override
	public boolean isUnlockable(Player player) {
		double averageScore = titleService.getAverageScoreFor(player);
		return averageScore >= 4;
	}

}