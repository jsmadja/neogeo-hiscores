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
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import javax.annotation.PostConstruct;

@ManagedBean
public class GamesBean {

    @EJB
    GameService gameService;

    @EJB
    ScoreService scoreService;

    List<GameItem> gameItems = new ArrayList<GameItem>();

    @PostConstruct
    public void init() {
        loadGameItems();
    }

    private void loadGameItems() {
        List<Object[]> scoreCounts = gameService.findAllScoreCountForEachGames();
        for (Object[] scoreCount : scoreCounts) {
            Long gameId = (Long) scoreCount[0];
            String gameName = (String) scoreCount[1];
            Long count = (Long) scoreCount[2];
            GameItem gameItem = new GameItem(gameName, gameId, count);
            gameItems.add(gameItem);
        }
    }

    public List<GameItem> getGames() {
        return gameItems;
    }

}
