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

import java.util.Arrays;
import java.util.List;

public class SlugerTitleStrategy extends AbstractUnlockingByScoringInManyGamesStrategy {

    @Override
    List<String> getGameNamesToScore() {
        return Arrays.asList("Metal Slug 2: Super Vehicle-001/II", "Metal Slug 3", "Metal Slug 4", "Metal Slug 5", "Metal Slug X: Super Vehicle-001", "Metal Slug: Super Vehicle-001");
    }

}
