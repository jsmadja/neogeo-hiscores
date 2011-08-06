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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Comparator;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Player;

public class PlayerSortedByPointsDescComparatorTest {

    Comparator<Player> comparator = new PlayerSortedByPointsDescComparator();

    @Test
    public void should_return_positive_number() {
        Player player1 = new Player("fullname1");
        player1.setPoints(3);

        Player player2 = new Player("fullname2");
        player2.setPoints(5);

        int comparison = comparator.compare(player1, player2);
        assertTrue(comparison > 0);
    }

    @Test
    public void should_return_zero() {
        Player player1 = new Player("fullname1");
        player1.setPoints(5);

        Player player2 = new Player("fullname2");
        player2.setPoints(5);

        int comparison = comparator.compare(player1, player2);
        assertEquals(0, comparison);
    }

    @Test
    public void should_return_negative_number() {
        Player player1 = new Player("fullname1");
        player1.setPoints(3);

        Player player2 = new Player("fullname2");
        player2.setPoints(1);

        int comparison = comparator.compare(player1, player2);
        assertTrue(comparison < 0);
    }
}
