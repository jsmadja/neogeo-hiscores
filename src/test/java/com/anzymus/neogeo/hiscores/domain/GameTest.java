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
import java.util.List;
import org.junit.Test;

public class GameTest {

    @Test
    public void should_add_level() {
        Game game = new Game("Fatal Fury");

        Level levelEasy = new Level("Easy");
        game.addLevel(1, levelEasy);

        List<Level> levels = game.getLevels();

        assertEquals(1, levels.size());
        assertEquals(levelEasy, levels.get(0));
    }

    @Test
    public void should_order_level_by_position() {
        Game game = new Game("Fatal Fury");

        Level levelHard = new Level("Hard");
        Level levelEasy = new Level("Easy");
        Level levelNormal = new Level("Normal");

        game.addLevel(2, levelHard);
        game.addLevel(0, levelEasy);
        game.addLevel(1, levelNormal);

        List<Level> levels = game.getLevels();

        System.err.println(levels);

        assertEquals(3, levels.size());
        assertEquals(levelEasy, levels.get(0));
        assertEquals(levelNormal, levels.get(1));
        assertEquals(levelHard, levels.get(2));
    }
    
    @Test
    public void should_be_equal() {
        Game game1 = new Game("game");
        Game game2 = new Game("game");
        assertEquals(game1, game2);
    }
}
