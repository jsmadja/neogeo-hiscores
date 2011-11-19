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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import com.anzymus.neogeo.hiscores.service.TitleService;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

public class PlayerBeanTest {

    @Mock
    ScoreService scoreService;

    @Mock
    PlayerService playerService;

    @Mock
    GameService gameService;

    @Mock
    TitleService titleService;

    @InjectMocks
    PlayerBean playerBean;

    Map<Title, TitleUnlockingStrategy> strategies;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        strategies = new HashMap<Title, TitleUnlockingStrategy>();
        when(titleService.findAllStrategies()).thenReturn(strategies);
    }

    @Test
    public void should_get_empty_scores_of_player() {
        List<ScoreItem> scoreItems = playerBean.getScores();
        assertTrue(scoreItems.isEmpty());
    }

    @Test
    public void should_get_one_element() {
        playerBean.setFullname("fullname");

        Player player = new Player("fullname");
        when(playerService.findByFullname("fullname")).thenReturn(player);

        Game game = new Game("Fatal Fury");
        List<Game> games = new ArrayList<Game>();
        games.add(game);
        when(gameService.findAllGamesPlayedBy(Mockito.any(Player.class))).thenReturn(games);

        Score score = new Score("123", player, "MVS", game, "http://");
        score.setId(1L);
        Scores scores = new Scores();
        scores.add(score);
        when(scoreService.findAllByGame(game)).thenReturn(scores);

        playerBean.init();
        List<ScoreItem> scoreItems = playerBean.getScores();
        assertEquals(1, scoreItems.size());
        ScoreItem scoreItem = scoreItems.get(0);
        assertEquals(game, scoreItem.getGame());
        assertEquals(1, scoreItem.getId());
        assertEquals("MVS", scoreItem.getLevel());
        assertEquals("http://", scoreItem.getPictureUrl());
        assertEquals(1, scoreItem.getRankNumber());
    }

    @Test
    public void should_print_gaps() {
        Player player1 = new Player("fullname1");
        Player player2 = new Player("fullname2");
        Player player3 = new Player("fullname3");

        Game game = new Game("Fatal Fury");
        List<Game> games = new ArrayList<Game>();
        games.add(game);
        when(gameService.findAllGamesPlayedBy(Mockito.any(Player.class))).thenReturn(games);

        Score score1 = new Score("1000", player1, "MVS", game, "http://");
        score1.setId(1L);

        Score score2 = new Score("500", player2, "MVS", game, "http://");
        score2.setId(2L);

        Score score3 = new Score("50", player3, "MVS", game, "http://");
        score3.setId(3L);

        Scores scores = new Scores();
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);
        when(scoreService.findAllByGame(game)).thenReturn(scores);

        when(playerService.findByFullname("fullname2")).thenReturn(player2);

        playerBean.setFullname("fullname2");

        playerBean.init();
        List<ScoreItem> scoreItems = playerBean.getScores();

        assertEquals(1, scoreItems.size());
        ScoreItem scoreItem = scoreItems.get(0);

        assertEquals("500", scoreItem.getValue());
        assertEquals("500", scoreItem.getNegativeGap());
        assertEquals("450", scoreItem.getPositiveGap());
    }

    @Test
    public void should_load_titles() {
        Title title1 = new Title();
        title1.setId(1L);
        title1.setLabel("title1");
        title1.setPosition(1L);
        Title title2 = new Title();
        title2.setId(2L);
        title2.setLabel("title2");
        title2.setPosition(2L);

        Player player = new Player("fullname");
        UnlockedTitle unlockedTitle1 = new UnlockedTitle(player, title1);

        player.getUnlockedTitles().add(unlockedTitle1);
        when(playerService.findByFullname("fullname")).thenReturn(player);

        TitleUnlockingStrategy titleUnlockingStrategy = null;
        strategies.put(title1, titleUnlockingStrategy);
        strategies.put(title2, titleUnlockingStrategy);

        playerBean.setFullname("fullname");
        playerBean.init();

        Collection<TitleItem> titleItems = playerBean.getTitles();

        Iterator<TitleItem> iterator = titleItems.iterator();
        TitleItem titleItem = iterator.next();
        assertEquals(title1, titleItem.getTitle());
        assertTrue(titleItem.isUnlocked());

        titleItem = iterator.next();
        assertEquals(title2, titleItem.getTitle());
        assertFalse(titleItem.isUnlocked());

    }
}
