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

package com.anzymus.neogeo.hiscores.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Level;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;

public class ScoreServiceTest {

    ScoreService scoreService = new ScoreService();

    @Test
    public void should_add_hiscore() {
        Game game = new Game("Fatal Fury");
        Player player = new Player("Anzymus", "ANZ");
        Level level = new Level("MVS");
        Score score = new Score("100", player, level, game);
        scoreService.add(score);

        Scores scores = scoreService.findAllByGame(game);

        System.err.println(scores);

        assertTrue(scores.contains(score));
    }

    @Test
    public void should_have_no_score() {
        Game game = new Game("Fatal Fury");
        Scores scores = scoreService.findAllByGame(game);
        assertEquals(0, scores.count());
    }

    @Test
    public void should_find_scores_by_player() {
        Player player = new Player("Anzymus", "ANZ");
        Score score1 = new Score("100", player, new Level("MVS"), new Game("Fatal Fury"));
        Score score2 = new Score("150", player, new Level("Normal"), new Game("Fatal Fury"));
        Score score3 = new Score("1mn32", player, new Level("Easy"), new Game("Samurai Shodown"));

        scoreService.add(score1);
        scoreService.add(score2);
        scoreService.add(score3);

        Scores scores = scoreService.findAllByPlayer(player);

        assertEquals(3, scores.count());

        System.err.println(scores);

        assertTrue(scores.contains(score1));
        assertTrue(scores.contains(score2));
        assertTrue(scores.contains(score3));
    }

    @Test
    public void should_order_scores_by_game() {
        Game gameFatalFury = new Game("Fatal Fury");
        Game gameSamuraiShodown = new Game("Samurai Shodown");

        Player player = new Player("Anzymus", "ANZ");
        Score score1 = new Score("100", player, new Level("MVS"), gameFatalFury);
        Score score2 = new Score("1mn32", player, new Level("Easy"), gameSamuraiShodown);
        Score score3 = new Score("150", player, new Level("Normal"), gameFatalFury);

        scoreService.add(score1);
        scoreService.add(score2);
        scoreService.add(score3);

        Scores scores = scoreService.findAllByPlayer(player);

        System.err.println(scores);

        Score[] array = scores.toArray();
        assertEquals(gameFatalFury, array[0].getGame());
        assertEquals(gameFatalFury, array[1].getGame());
        assertEquals(gameSamuraiShodown, array[2].getGame());
    }

    @Test
    public void should_order_scores_by_level() {
        Game gameFatalFury = new Game("Fatal Fury");
        Player player = new Player("Anzymus", "ANZ");

        Score score1 = new Score("100", player, new Level("3"), gameFatalFury);
        Score score2 = new Score("150", player, new Level("2"), gameFatalFury);
        Score score3 = new Score("100", player, new Level("4"), gameFatalFury);
        Score score4 = new Score("150", player, new Level("1"), gameFatalFury);

        scoreService.add(score1);
        scoreService.add(score2);
        scoreService.add(score3);
        scoreService.add(score4);

        Scores scores = scoreService.findAllByPlayer(player);

        System.err.println(scores);

        Score[] array = scores.toArray();
        assertEquals("1", array[0].getLevel().getLabel());
        assertEquals("2", array[1].getLevel().getLabel());
        assertEquals("3", array[2].getLevel().getLabel());
        assertEquals("4", array[3].getLevel().getLabel());
    }

    @Test
    public void should_order_by_game_then_level_then_score() {
        Game gameFatalFury = new Game("Fatal Fury");
        Game gameSamuraiShodown = new Game("Samurai Shodown");

        Player player = new Player("Anzymus", "ANZ");

        Score score1 = new Score("100", player, new Level("3"), gameFatalFury);
        Score score2 = new Score("150", player, new Level("2"), gameFatalFury);
        Score score3 = new Score("130", player, new Level("3"), gameFatalFury);
        Score score4 = new Score("150", player, new Level("1"), gameFatalFury);

        Score score5 = new Score("a", player, new Level("3"), gameSamuraiShodown);
        Score score6 = new Score("d", player, new Level("2"), gameSamuraiShodown);
        Score score7 = new Score("b", player, new Level("4"), gameSamuraiShodown);
        Score score8 = new Score("c", player, new Level("1"), gameSamuraiShodown);

        scoreService.add(score1);
        scoreService.add(score2);
        scoreService.add(score3);
        scoreService.add(score4);
        scoreService.add(score5);
        scoreService.add(score6);
        scoreService.add(score7);
        scoreService.add(score8);

        Scores scores = scoreService.findAllByPlayer(player);

        System.err.println(scores);

        Score[] array = scores.toArray();

        assertEquals(gameFatalFury, array[0].getGame());
        assertEquals("1", array[0].getLevel().getLabel());

        assertEquals(gameFatalFury, array[1].getGame());
        assertEquals("2", array[1].getLevel().getLabel());

        assertEquals(gameFatalFury, array[2].getGame());
        assertEquals("3", array[2].getLevel().getLabel());
        assertEquals("100", array[2].getValue());

        assertEquals(gameFatalFury, array[3].getGame());
        assertEquals("3", array[3].getLevel().getLabel());
        assertEquals("130", array[3].getValue());

        assertEquals(gameSamuraiShodown, array[4].getGame());
        assertEquals("1", array[4].getLevel().getLabel());

        assertEquals(gameSamuraiShodown, array[5].getGame());
        assertEquals("2", array[5].getLevel().getLabel());

        assertEquals(gameSamuraiShodown, array[6].getGame());
        assertEquals("3", array[6].getLevel().getLabel());

        assertEquals(gameSamuraiShodown, array[7].getGame());
        assertEquals("4", array[7].getLevel().getLabel());
    }
}
