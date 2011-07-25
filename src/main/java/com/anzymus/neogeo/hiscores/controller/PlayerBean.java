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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class PlayerBean {
    
    @EJB ScoreService scoreService;

    @EJB PlayerService playerService;
    
    @ManagedProperty(value="#{param.fullname}")
    private String fullname;
    
    private Map<Game, GameItem> gameItems = new HashMap<Game, GameItem>();
    
    @PostConstruct
    public void init() {
        Player player = playerService.findByFullname(fullname);
        Scores scores = scoreService.findAllByPlayer(player);
        
        for(Score score:scores) {
            Game game = score.getGame();
            String level = score.getLevel();
            
            GameItem gameItem = gameItems.get(game);
            if (gameItem == null) {
                gameItem = new GameItem(game.getName(), game.getId());
                gameItems.put(game, gameItem);
            }
            gameItem.addScore(level, score);
        }
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<GameItem> getGames() {
        List<GameItem> games = new ArrayList<GameItem>();
        games.addAll(gameItems.values());
        Collections.sort(games, sortByNameComparator);
        return games;
    }
    
    private static Comparator<GameItem> sortByNameComparator = new Comparator<GameItem>() {
        @Override
        public int compare(GameItem item1, GameItem item2) {
            return item1.getName().compareTo(item2.getName());
        }
    };
}