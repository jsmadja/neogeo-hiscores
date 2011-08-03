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
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

public class GamesBeanTest {

    @Mock
    GameService gameService;

    @Mock
    ScoreService scoreService;

    GamesBean gamesBean;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        gamesBean = new GamesBean();
        gamesBean.gameService = gameService;
        gamesBean.scoreService = scoreService;
    }

    @Test
    public void should_get_game_list_with_scores() {
        Object[] count = new Object[] { 0L, "Fatal Fury", 5L };

        List<Object[]> scoreCount = new ArrayList<Object[]>();
        scoreCount.add(count);

        when(gameService.findAllScoreCountForEachGames()).thenReturn(scoreCount);

        List<GameItem> games = gamesBean.getGames();
        GameItem gameItem = games.get(0);
        assertEquals(0, gameItem.getId());
        assertEquals("Fatal Fury", gameItem.getName());
        assertEquals(5L, gameItem.getCount());
    }
}
