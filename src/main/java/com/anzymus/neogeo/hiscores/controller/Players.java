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

package com.anzymus.neogeo.hiscores.controller;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collections;
import java.util.List;

import com.anzymus.neogeo.hiscores.comparator.ScoreComparator;
import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;

public class Players {

	static void extractScoreItemsFromLevels(List<ScoreItem> scoreItems, Game game, Scores scores, String level,
			String fullname) {
		List<Score> scoreList = scores.getScoresByLevel(level);
		Collections.sort(scoreList, new ScoreSortedByValueDescComparator());
		Score previousScore = null;
		Score nextScore = null;
		int previousRank = 0;
		for (int i = 0; i < scoreList.size(); i++) {
			Score score = scoreList.get(i);
			nextScore = (i + 1) < scoreList.size() ? scoreList.get(i + 1) : null;
			int rank = i + 1;
			if (isCurrentPlayer(score, fullname)) {
				String value = score.getValue();
				if (isSameRankAsPreviousScore(previousScore, value)) {
					rank = previousRank;
				}
				Long scoreId = score.getId();
				String scorePictureUrl = score.getPictureUrl();
				ScoreItem scoreItem = createScoreItem(score, game, level, rank, value, scoreId, scorePictureUrl);
				scoreItem.setPositiveGap(computeGap(score, nextScore));
				scoreItem.setNegativeGap(computeGap(score, previousScore));
				scoreItems.add(scoreItem);
			}
			previousScore = score;
			previousRank = rank;
		}
	}

	private static boolean isCurrentPlayer(Score score, String fullname) {
		return score.getPlayer().getFullname().equals(fullname);
	}

	private static ScoreItem createScoreItem(Score score, Game game, String level, int rank, String value,
			Long scoreId, String scorePictureUrl) {
		ScoreItem scoreItem = new ScoreItem(score);
		scoreItem.setRankNumber(rank);
		scoreItem.setValue(value);
		scoreItem.setLevel(level);
		scoreItem.setGame(game);
		scoreItem.setId(scoreId);
		scoreItem.setPictureUrl(scorePictureUrl);
		return scoreItem;
	}

	private static String computeGap(Score score1, Score score2) {
		String gap = "";
		if (score2 != null) {
			gap = ScoreComparator.gap(score1, score2);
		}
		return gap;
	}

	private static boolean isSameRankAsPreviousScore(Score previousScore, String scoreValue) {
		return previousScore != null && previousScore.getValue().equals(scoreValue);
	}

	public static void discoverImprovableScores(List<ScoreItem> scoreItems) {
		int minimumNegativeGapValue = Integer.MAX_VALUE;
		ScoreItem improvableScore = null;
		for (ScoreItem scoreItem : scoreItems) {
			String negativeGap = scoreItem.getNegativeGap();
			Score score = scoreItem.getScore();
			boolean isDiscoverable = score.isClassicScore() && isNotBlank(negativeGap);
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
