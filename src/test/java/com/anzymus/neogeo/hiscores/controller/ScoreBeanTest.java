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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.clients.AuthenticationFailed;
import com.anzymus.neogeo.hiscores.clients.NeoGeoFansClient;
import com.anzymus.neogeo.hiscores.clients.NeoGeoFansClientFactory;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

public class ScoreBeanTest {

    @Mock
    ScoreService scoreService;

    @Mock
    PlayerService playerService;

    @Mock
    GameService gameService;

    @Mock
    NeoGeoFansClientFactory neoGeoFansClientFactory;

    @Mock
    NeoGeoFansClient neoGeoFansClient;

    @Mock
    FacesContext facesContext;

    ScoreBean scoreBean;

    private Player player;

    private Game game;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        scoreBean = new ScoreBean();
        scoreBean.gameService = gameService;
        scoreBean.playerService = playerService;
        scoreBean.scoreService = scoreService;
        scoreBean.neoGeoFansClientFactory = neoGeoFansClientFactory;
        scoreBean.facesContext = facesContext;

        when(neoGeoFansClientFactory.create()).thenReturn(neoGeoFansClient);

        String playerName = "fullname";
        player = new Player(playerName);
        when(playerService.findByFullname("fullname")).thenReturn(player);

        game = new Game("Fatal Fury");
        long gameId = 19L;
        game.setId(gameId);
        when(gameService.findById(19L)).thenReturn(game);
    }

    @Test
    public void should_init_bean_with_score_from_db() {
        String playerName = "fullname";
        Player player = new Player(playerName);

        Game game = new Game("Fatal Fury");
        long gameId = 19L;
        game.setId(gameId);

        String pictureUrl = "http://";
        String level = "MVS";
        String scoreValue = "765465";
        String message = "mon message";
        Score score = new Score(scoreValue, player, level, game, pictureUrl);
        score.setMessage(message);

        when(scoreService.findById(1234)).thenReturn(score);

        scoreBean.setId("1234");
        scoreBean.init();

        assertEquals(playerName, scoreBean.getFullname());
        assertEquals(scoreValue, scoreBean.getScore());
        assertEquals(message, scoreBean.getMessage());
        assertEquals(pictureUrl, scoreBean.getPictureUrl());
        assertEquals(level, scoreBean.getCurrentLevel());
        assertEquals(gameId, scoreBean.getCurrentGame());
    }

    @Test
    public void should_add_score_in_db() throws AuthenticationFailed {
        when(neoGeoFansClient.authenticate(anyString(), anyString())).thenReturn(true);

        String scoreValue = "12432";
        String pictureUrl = "http://";
        String level = "Easy";
        String message = "mon message";
        String password = "mypassword";

        scoreBean.setScore(scoreValue);
        scoreBean.setCurrentGame(game.getId());
        scoreBean.setCurrentLevel(level);
        scoreBean.setMessage(message);
        scoreBean.setFullname("fullname");
        scoreBean.setPassword(password);
        scoreBean.setPictureUrl(pictureUrl);

        String redirection = scoreBean.add();
        assertEquals("home", redirection);

        Score scoreToAdd = new Score(scoreValue, player, level, game, pictureUrl);

        verify(neoGeoFansClient).authenticate(player.getFullname(), password);
        verify(scoreService).store(scoreToAdd);
    }

    @Test
    public void should_post_in_ngf_if_mvs_level() throws AuthenticationFailed {
        when(neoGeoFansClient.authenticate(anyString(), anyString())).thenReturn(true);

        String scoreValue = "12432";
        String pictureUrl = "http://";
        String level = "MVS";
        String message = "mon message";
        String password = "mypassword";

        scoreBean.setScore(scoreValue);
        scoreBean.setCurrentGame(game.getId());
        scoreBean.setCurrentLevel(level);
        scoreBean.setMessage(message);
        scoreBean.setFullname("fullname");
        scoreBean.setPassword(password);
        scoreBean.setPictureUrl(pictureUrl);

        String redirection = scoreBean.add();
        assertEquals("home", redirection);

        verify(neoGeoFansClient).post(anyString(), anyInt());
    }

    @Test
    public void should_show_error_when_login_password_are_invalids_when_add() throws AuthenticationFailed {
        when(neoGeoFansClient.authenticate(anyString(), anyString())).thenReturn(false);

        String redirection = scoreBean.add();
        assertEquals("score/create", redirection);

        verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
    }

    @Test
    public void should_show_error_when_login_password_are_invalids_when_edit() throws AuthenticationFailed {
        when(neoGeoFansClient.authenticate(anyString(), anyString())).thenReturn(false);

        String redirection = scoreBean.edit();
        assertEquals("score/edit", redirection);

        verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
    }

    @Test
    public void should_edit_score() throws AuthenticationFailed {
        when(neoGeoFansClient.authenticate(anyString(), anyString())).thenReturn(true);

        scoreBean.setId("12");

        Score score = new Score();
        when(scoreService.findById(12L)).thenReturn(score);

        String redirection = scoreBean.edit();
        assertEquals("home", redirection);

        verify(scoreService).store(score);
    }
}
