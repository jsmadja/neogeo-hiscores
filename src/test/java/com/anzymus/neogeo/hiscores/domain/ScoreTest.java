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

package com.anzymus.neogeo.hiscores.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class ScoreTest {

    @Test
    public void should_not_be_equal() {
        Player player1 = new Player("abc");
        String level1 = "MVS";
        Game game1 = new Game("game");
        String pictureUrl1 = "pictureUrl";

        Score score1 = new Score("1", player1, level1, game1, pictureUrl1);

        Player player2 = new Player("def");
        String level2 = "MVS";
        Game game2 = new Game("game");
        String pictureUrl2 = "pictureUrl";

        Score score2 = new Score("1", player2, level2, game2, pictureUrl2);

        assertFalse(score1.equals(score2));
    }

    @Test
    public void should_be_equal() {
        Player player1 = new Player("abc");
        String level1 = "MVS";
        Game game1 = new Game("game");
        String pictureUrl1 = "pictureUrl";

        Score score1 = new Score("1", player1, level1, game1, pictureUrl1);

        Player player2 = new Player("abc");
        String level2 = "MVS";
        Game game2 = new Game("game");
        String pictureUrl2 = "pictureUrl";

        Score score2 = new Score("1", player2, level2, game2, pictureUrl2);

        assertEquals(score1, score2);
    }

    @Test
    public void should_not_be_equal_when_passing_null() {
        Score score = new Score();
        assertFalse(score.equals(null));
    }

    @Test
    public void should_print_value() {
        Score score = new Score("123");
        assertEquals("123", score.toString());
    }

}
