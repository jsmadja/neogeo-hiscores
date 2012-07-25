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

public abstract class AbstractMinimumAllClearTitleStrategy extends AbstractTitleStrategy {

    @Override
    public boolean isUnlockable(Player player) {
        return player.getNumAllClearsByPlayer() >= getNumAllClearsToUnlock();
    }

    @Override
    public Achievement getAchievementFor(Player player) {
        long numAllClears = player.getNumAllClearsByPlayer();
        if (numAllClears > getNumAllClearsToUnlock()) {
            numAllClears = getNumAllClearsToUnlock();
        }
        Achievement achievement = new Achievement(getTitle(), percent(numAllClears, getNumAllClearsToUnlock()));
        achievement.addStep(new Step(getStepName(), numAllClears >= getNumAllClearsToUnlock()));
        return achievement;
    }

    protected String getStepName() {
        long numAllClears = getNumAllClearsToUnlock();
        return "Finish " + numAllClears + " game" + (numAllClears > 1 ? "s" : "") + " with only one credit";
    }

    protected abstract long getNumAllClearsToUnlock();

}
