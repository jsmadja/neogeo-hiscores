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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class GamesBean {

    @EJB
    GameService gameService;

    @EJB
    ScoreService scoreService;

    public List<GameItem> getGames() {
        List<Game> games = gameService.findAllPlayedGames();
        List<GameItem> gameItems = new ArrayList<GameItem>();
        for (Game game : games) {
            long count = scoreService.findCountByGame(game);
            if (count > 0) {
                GameItem gameItem = new GameItem(game.getName(), game.getId(), count);
                gameItems.add(gameItem);
            }
        }
        return gameItems;
    }

}
