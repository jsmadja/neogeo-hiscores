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

import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreTest {

    @Test
    public void should_not_be_equal() {
        Player player1 = new Player("abc", "ABC");
        Level level1 = new Level("MVS");
        Game game1 = new Game("game");
        String pictureUrl1 = "pictureUrl";
        
        Score score1 = new Score("1", player1, level1, game1, pictureUrl1);
        
        Player player2 = new Player("def", "ABC");
        Level level2 = new Level("MVS");
        Game game2 = new Game("game");
        String pictureUrl2 = "pictureUrl";
        
        Score score2 = new Score("1", player2, level2, game2, pictureUrl2);
        
        assertFalse(score1.equals(score2));
    }
    
    @Test
    public void should_be_equal() {
        Player player1 = new Player("abc", "ABC");
        Level level1 = new Level("MVS");
        Game game1 = new Game("game");
        String pictureUrl1 = "pictureUrl";
        
        Score score1 = new Score("1", player1, level1, game1, pictureUrl1);
        
        Player player2 = new Player("abc", "ABC");
        Level level2 = new Level("MVS");
        Game game2 = new Game("game");
        String pictureUrl2 = "pictureUrl";
        
        Score score2 = new Score("1", player1, level1, game1, pictureUrl1);
        
        assertEquals(score1, score2);
    }
    
}
