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

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Game;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameServiceTest {

    GameService gameService;

    @Mock
    EntityManager em;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameService();
        gameService.em = em;
    }
    
    @Ignore
    @Test
    public void should_add_new_game() {
        Game game = new Game("Fatal Fury");
        game.setRules("Objectif : Faire le maximum de points avec 1 credit.");

        gameService.store(game);
        List<Game> games = gameService.findAll();
        assertTrue(games.contains(game));
    }

}
