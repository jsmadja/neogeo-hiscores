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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;

@Ignore
public class ScoreServiceTest {

    ScoreService scoreService;

    String pictureUrl = "http://www.imageshack.com";

    @Mock
    EntityManager em;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        scoreService = new ScoreService();
        scoreService.em = em;
    }

    @Test
    public void should_add_hiscore() {
        Game game = new Game("Fatal Fury");
        Player player = new Player("Anzymus");
        String level = "MVS";
        Score score = new Score("100", player, level, game, pictureUrl);
        scoreService.store(score);

        Scores scores = scoreService.findAllByGame(game);

        assertTrue(scores.contains(score));
    }

    @Test
    public void should_find_scores_by_player() {
        Player player = new Player("Anzymus");
        Score score1 = new Score("100", player, "MVS", new Game("Fatal Fury"), pictureUrl);
        Score score2 = new Score("150", player, "Normal", new Game("Fatal Fury"), pictureUrl);
        Score score3 = new Score("1mn32", player, "Easy", new Game("Samurai Shodown"), pictureUrl);

        scoreService.store(score1);
        scoreService.store(score2);
        scoreService.store(score3);

        Scores scores = scoreService.findAllByPlayer(player);

        assertEquals(3, scores.count());

        assertTrue(scores.contains(score1));
        assertTrue(scores.contains(score2));
        assertTrue(scores.contains(score3));
    }

    @Test
    public void should_find_last_scores_order_by_date_desc() {
        List<Score> scores = scoreService.findLastScoresOrderByDateDesc();
        assertNotNull(scores);
    }
}
