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
