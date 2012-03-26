package com.anzymus.neogeo.hiscores.success;

import com.anzymus.neogeo.hiscores.domain.Player;

public class LowTierTitleStrategy extends AbstractTitleStrategy {

	@Override
	public boolean isUnlockable(Player player) {
		double averageScore = titleService.getAverageScoreFor(player);
		return averageScore >= 1;
	}

}
