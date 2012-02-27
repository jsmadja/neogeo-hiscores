package com.anzymus.neogeo.hiscores.domain;

import com.google.common.base.Objects;

public class SoccerWithGoalAverageScore implements Comparable<SoccerWithGoalAverageScore> {

	private Integer games;
	private Integer won;
	private Integer goalsFor;
	private Integer goalAgainst;
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
			goalAgainst = Integer.parseInt(splits[3].split("\\+")[0]);
			goalDifference = Integer.parseInt(splits[3].split("\\+")[1]);
		} else {
			goalAgainst = Integer.parseInt(splits[3]);
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
