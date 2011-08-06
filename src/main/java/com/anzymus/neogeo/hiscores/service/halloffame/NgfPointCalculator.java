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

package com.anzymus.neogeo.hiscores.service.halloffame;

class NgfPointCalculator implements PointCalculator {
    private static final int[] POINTS = new int[] { 10, 8, 6, 5, 4, 3, 2, 1 };

    @Override
    public int getPointsByIndex(int i, int maxPoints) {
        if (i >= POINTS.length || i < 0) {
            return 0;
        }
        return POINTS[i];
    }
}
