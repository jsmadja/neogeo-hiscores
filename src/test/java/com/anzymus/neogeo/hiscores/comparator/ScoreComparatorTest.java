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
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreComparatorTest {

    @Test
    public void should_return_max_of_two_scores_as_int() {
        Game game = new Game("Fatal Fury");
        String level = "MVS";
        String picture = "http://";
        Player player = new Player("anz");
        Score score1 = new Score("1", player, level, game, picture);
        Score score2 = new Score("1", player, level, game, picture);
        assertEquals(score2, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_find_gap_for_int() {
        String score1 = "123";
        String score2 = "345";
        assertEquals("222", ScoreComparator.gap(score1, score2));
    }

}
