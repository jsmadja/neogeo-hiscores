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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NgfPointCalculatorTest {

    PointCalculator calculator = new NgfPointCalculator();

    @Test
    public void should_do_basic_calculation() {
        int maxPoints = 1000;
        assertEquals(10, calculator.getPointsByIndex(0, maxPoints));
        assertEquals(8, calculator.getPointsByIndex(1, maxPoints));
        assertEquals(6, calculator.getPointsByIndex(2, maxPoints));
        assertEquals(5, calculator.getPointsByIndex(3, maxPoints));
        assertEquals(4, calculator.getPointsByIndex(4, maxPoints));
        assertEquals(3, calculator.getPointsByIndex(5, maxPoints));
        assertEquals(2, calculator.getPointsByIndex(6, maxPoints));
        assertEquals(1, calculator.getPointsByIndex(7, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(8, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(9, maxPoints));
    }

}
