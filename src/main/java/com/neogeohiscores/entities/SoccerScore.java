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

package com.neogeohiscores.entities;

import static java.lang.Math.abs;

public class SoccerScore implements Comparable<SoccerScore> {

    private Integer won;
    private Integer goalsFor;
    private Integer goalAgainst;

    public SoccerScore(Score score) {
        String scoreValue = score.getValue();
        String[] splits = scoreValue.split("-");
        won = Integer.parseInt(splits[0]);
        goalsFor = Integer.parseInt(splits[1]);
        goalAgainst = Integer.parseInt(splits[2]);
    }

    public String gap(SoccerScore score) {
        int gapWon = abs(won - score.won);
        int gapGoalsFor = abs(goalsFor - score.goalsFor);
        int gapGoalAgainst = abs(goalAgainst - score.goalAgainst);
        return gapWon + "-" + gapGoalsFor + "-" + gapGoalAgainst;
    }

    @Override
    public int compareTo(SoccerScore score) {
        if (won != score.won) {
            return score.won.compareTo(won);
        }
        if (goalsFor != score.goalsFor) {
            return score.goalsFor.compareTo(goalsFor);
        }
        if (goalAgainst != score.goalAgainst) {
            return goalAgainst.compareTo(score.goalAgainst);
        }
        return 0;
    }

}
