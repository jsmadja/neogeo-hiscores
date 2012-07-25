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

package com.anzymus.neogeo.hiscores.success;

import com.neogeohiscores.entities.Achievement;
import com.neogeohiscores.entities.Player;

public class MiddleTierTitleStrategy extends AbstractTitleStrategy {

    private static final int MINIMUM_SCORE = 4;

    @Override
    public boolean isUnlockable(Player player) {
        double averageScore = player.getAverageScoreFor();
        return averageScore >= MINIMUM_SCORE;
    }

    @Override
    public Achievement getAchievementFor(Player player) {
        double averageScore = player.getAverageScoreFor();
        boolean completed = averageScore >= MINIMUM_SCORE;
        if (averageScore > MINIMUM_SCORE) {
            averageScore = MINIMUM_SCORE;
        }
        Achievement achievement = new Achievement(title, percent((long) (averageScore * 10), MINIMUM_SCORE * 10));
        Step step = new Step("Have an average score greater or equal to " + MINIMUM_SCORE, completed);
        achievement.addStep(step);
        return achievement;
    }

}
