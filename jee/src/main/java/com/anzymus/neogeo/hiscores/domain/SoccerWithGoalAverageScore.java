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

package com.anzymus.neogeo.hiscores.domain;

import com.google.common.base.Objects;

public class SoccerWithGoalAverageScore implements Comparable<SoccerWithGoalAverageScore> {

	private Integer games;
	private Integer won;
	private Integer goalsFor;
	private Integer goalDifference;
	private Score score;

	public SoccerWithGoalAverageScore(Score score) {
		this.score = score;
		String scoreValue = score.getValue();
		String[] splits = scoreValue.split("-");
		games = Integer.parseInt(splits[0]);
		won = Integer.parseInt(splits[1]);
		goalsFor = Integer.parseInt(splits[2]);
		if (splits[3].contains("+")) {
			goalDifference = Integer.parseInt(splits[3].split("\\+")[1]);
		} else {
			goalDifference = -Integer.parseInt(splits[4]);
		}
	}

	@Override
	public int compareTo(SoccerWithGoalAverageScore score) {
		if (games != score.games) {
			return score.games.compareTo(games);
		}
		if (won != score.won) {
			return score.won.compareTo(won);
		}
		if (goalDifference != score.goalDifference) {
			return score.goalDifference.compareTo(goalDifference);
		}
		if (goalsFor != score.goalsFor) {
			return score.goalsFor.compareTo(goalsFor);
		}
		return 0;
	}

	public Score getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(score.getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SoccerWithGoalAverageScore) {
			SoccerWithGoalAverageScore score = (SoccerWithGoalAverageScore) obj;
			return score.getScore().getValue().equals(getScore().getValue());
		}
		return false;
	}

}
