package com.anzymus.neogeo.hiscores.controller;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;

import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreItems {

	public static void discoverImprovableScores(List<ScoreItem> scoreItems) {
		int minimumNegativeGapValue = Integer.MAX_VALUE;
		ScoreItem improvableScore = null;
		for (ScoreItem scoreItem : scoreItems) {
			String negativeGap = scoreItem.getNegativeGap();
			Score score = scoreItem.getScore();
			boolean isDiscoverable = score.isImprovable() && isNotBlank(negativeGap);
			if (isDiscoverable) {
				int negativeGapValue = Integer.valueOf(negativeGap);
				if (negativeGapValue < minimumNegativeGapValue) {
					minimumNegativeGapValue = negativeGapValue;
					improvableScore = scoreItem;
				}
			}
		}
		if (improvableScore != null) {
			improvableScore.setImprovable(true);
		}
	}
}
