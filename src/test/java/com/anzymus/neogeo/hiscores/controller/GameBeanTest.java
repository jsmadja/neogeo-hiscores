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

package com.anzymus.neogeo.hiscores.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

public class GameBeanTest {

    @Mock
    ScoreService scoreService;

    @Mock
    GameService gameService;

    GameBean gameBean;

    Game game = new Game("Fatal Fury");
    Scores scores;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        gameBean = new GameBean();
        gameBean.scoreService = scoreService;
        gameBean.gameService = gameService;

        scores = new Scores();
    }

    @Test
    public void should_init_bean_with_game_id() {
        when(gameService.findById(13)).thenReturn(game);
        when(scoreService.findAllByGame(game)).thenReturn(scores);

        gameBean.setId("13");
        gameBean.init();

        assertEquals("Fatal Fury", gameBean.getName());
        verify(scoreService).findAllByGame(game);
    }

    @Test
    public void should_build_level_items() {
        when(gameService.findById(anyLong())).thenReturn(game);
        when(scoreService.findAllByGame(any(Game.class))).thenReturn(scores);
        gameBean.setId("13");

        Player player = new Player("fullname");
        Score score = new Score("123", player, "MVS", game, "http://");
        score.setId(0L);
        scores.add(score);

        gameBean.init();
        List<LevelItem> levelItems = gameBean.getLevels();

        LevelItem levelItem = levelItems.get(0);

        List<ScoreItem> scoreItems = levelItem.getScoreItems();
        ScoreItem scoreItem = scoreItems.get(0);
        assertEquals("1st", scoreItem.getRank());
        assertEquals("123", scoreItem.getValue());
    }

    @Test
    public void verify_that_same_score_same_rank() {
        when(gameService.findById(anyLong())).thenReturn(game);
        when(scoreService.findAllByGame(any(Game.class))).thenReturn(scores);
        gameBean.setId("13");
        
        Player player1 = new Player("fullname1");
        Score score1 = new Score("123", player1, "MVS", game, "http://");
        score1.setId(0L);
        scores.add(score1);

        Player player2 = new Player("fullname2");
        Score score2 = new Score("123", player2, "MVS", game, "http://");
        score2.setId(0L);
        scores.add(score2);

        gameBean.init();
        List<LevelItem> levelItems = gameBean.getLevels();

        LevelItem levelItem = levelItems.get(0);

        List<ScoreItem> scoreItems = levelItem.getScoreItems();
        ScoreItem scoreItem1 = scoreItems.get(0);
        assertEquals("1st", scoreItem1.getRank());
        assertEquals("123", scoreItem1.getValue());
        assertEquals("fullname1", scoreItem1.getPlayer());

        ScoreItem scoreItem2 = scoreItems.get(1);
        assertEquals("1st", scoreItem2.getRank());
        assertEquals("123", scoreItem2.getValue());
        assertEquals("fullname2", scoreItem2.getPlayer());
    }
}
