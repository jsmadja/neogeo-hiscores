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

import org.junit.Test;

import com.anzymus.neogeo.hiscores.controller.PlayerItem;

public class PlayerItemsSortedWithAverageComparatorTest {

    PlayerItemsSortedWithAverageComparator comparator = new PlayerItemsSortedWithAverageComparator();

    @Test
    public void should_preserve_order_when_rank_are_not_equal() {
        PlayerItem playerItem1 = new PlayerItem();
        playerItem1.setContribution(9);
        playerItem1.setRank("5");

        PlayerItem playerItem2 = new PlayerItem();
        playerItem2.setContribution(10);
        playerItem2.setRank("4");

        int comparison = comparator.compare(playerItem1, playerItem2);

        assertEquals(0, comparison);
    }

    @Test
    public void should_order_by_average_when_rank_are_equal() {
        PlayerItem playerItem1 = new PlayerItem();
        playerItem1.setContribution(7);
        playerItem1.setRank("5");

        PlayerItem playerItem2 = new PlayerItem();
        playerItem2.setContribution(10);
        playerItem2.setRank("5");

        int comparison = comparator.compare(playerItem1, playerItem2);

        assertEquals(-1, comparison);
    }

}
