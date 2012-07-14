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

package com.anzymus.neogeo.hiscores.comparator;

import com.anzymus.neogeo.hiscores.domain.ChronoScore;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.SoccerScore;
import com.anzymus.neogeo.hiscores.domain.SoccerWithGoalAverageScore;

public class ScoreComparator {

	public static Score max(Score score1, Score score2) {
		try {
			if (areSoccer(score1, score2)) {
				return maxAsSoccer(score1, score2);
			}
			if (areSoccerWithGoalAverage(score1, score2)) {
				return maxAsSoccerWithGoalAverage(score1, score2);
			}
			return maxAsInt(score1, score2);
		} catch (Throwable t) {
			return score1;
		}
	}

	private static Score maxAsSoccer(Score score1, Score score2) {
		SoccerScore soccerScore1 = new SoccerScore(score1);
		SoccerScore soccerScore2 = new SoccerScore(score2);
		if (soccerScore1.compareTo(soccerScore2) > 0) {
			return score1;
		}
		return score2;
	}

	private static Score maxAsSoccerWithGoalAverage(Score score1, Score score2) {
		SoccerWithGoalAverageScore soccerScore1 = new SoccerWithGoalAverageScore(score1);
		SoccerWithGoalAverageScore soccerScore2 = new SoccerWithGoalAverageScore(score2);
		if (soccerScore1.compareTo(soccerScore2) < 0) {
			return score1;
		}
		return score2;
	}

	private static boolean areSoccer(Score score1, Score score2) {
		return score1.isSoccer() && score2.isSoccer();
	}

	private static boolean areSoccerWithGoalAverage(Score score1, Score score2) {
		return score1.isSoccerWithGoalAverage() && score2.isSoccerWithGoalAverage();
	}

	private static Score maxAsInt(Score score1, Score score2) {
		Integer score1asInt = Integer.parseInt(score1.getValue());
		Integer score2asInt = Integer.parseInt(score2.getValue());
		return score1asInt > score2asInt ? score1 : score2;
	}

	public static String gap(Score score1, Score score2) {
		try {
			if (areChrono(score1, score2)) {
				return gapAsChrono(score1, score2);
			}
			if (areSoccer(score1, score2)) {
				return gapAsSoccer(score1, score2);
			}
			return gapAsInt(score1, score2);
		} catch (Throwable t) {
			return "";
		}
	}

	private static boolean areChrono(Score score1, Score score2) {
		return score1.isChrono() && score2.isChrono();
	}

	private static String gapAsChrono(Score score1, Score score2) {
		ChronoScore chronoScore1 = new ChronoScore(score1);
		ChronoScore chronoScore2 = new ChronoScore(score2);
		return chronoScore1.gap(chronoScore2);
	}

	private static String gapAsSoccer(Score score1, Score score2) {
		SoccerScore soccerScore1 = new SoccerScore(score1);
		SoccerScore soccerScore2 = new SoccerScore(score2);
		return soccerScore1.gap(soccerScore2);
	}

	private static String gapAsInt(Score score1, Score score2) {
		Integer score1asInt = Integer.parseInt(score1.getValue());
		Integer score2asInt = Integer.parseInt(score2.getValue());
		return Integer.toString(Math.abs(score1asInt - score2asInt));
	}

}
