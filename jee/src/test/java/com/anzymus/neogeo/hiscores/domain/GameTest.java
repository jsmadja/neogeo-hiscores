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

public class GameTest {

    @Test
    public void should_be_equal() {
        Game game1 = new Game("game");
        Game game2 = new Game("game");
        assertEquals(game1, game2);
    }

    @Test
    public void should_compare_by_name_asc() {
        Game gameA = new Game("a");
        Game gameB = new Game("b");
        assertEquals("a".compareTo("b"), gameA.compareTo(gameB));
    }

    @Test
    public void should_print_name() {
        Game game = new Game("a");
        assertEquals("a", game.toString());
    }

    @Test
    public void should_be_false_when_equal_to_null() {
        assertFalse(new Game("a").equals(null));
    }
}
