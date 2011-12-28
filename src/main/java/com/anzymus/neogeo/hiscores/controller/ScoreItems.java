package com.anzymus.neogeo.hiscores.controller;

import static com.anzymus.neogeo.hiscores.common.IntegerToRank.getOrdinalFor;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
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

	public static List<ScoreItem> createScoreItems(List<Score> scores, int minScoresToShow) {
		Score oldScore = null;
		String oldRank = null;
		List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();
		int numScoreToShow = scores.size() > minScoresToShow ? scores.size() : minScoresToShow;
		for (int i = 0; i < numScoreToShow; i++) {
			String rank;
			ScoreItem scoreItem = new ScoreItem();
			if (i < scores.size()) {
				Score score = scores.get(i);
				scoreItem.setValue(score.getValue());
				scoreItem.setPlayer(score.getPlayer().getFullname());
				scoreItem.setPictureUrl(score.getPictureUrl());
				scoreItem.setId(score.getId());
				scoreItem.setMessage(score.getMessage());
				scoreItem.setStage(score.getStage());
				scoreItem.setDate(score.getCreationDate());
				scoreItem.setAllClear(score.getAllClear());
				if (oldScore != null && oldScore.getValue().equals(score.getValue())) {
					rank = oldRank;
				} else {
					rank = getOrdinalFor(i + 1);
				}
				oldScore = score;
			} else {
				scoreItem.setValue("");
				scoreItem.setPlayer("");
				oldScore = null;
				rank = getOrdinalFor(i + 1);
			}
			scoreItem.setRank(rank);
			scoreItems.add(scoreItem);
			oldRank = rank;
		}
		return scoreItems;
	}
}
