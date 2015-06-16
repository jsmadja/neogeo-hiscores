/**
 * Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neogeohiscores.web.services;

import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import org.hibernate.Query;

import java.util.List;

public class GameService extends GenericService<Game> {

    public GameService() {
        super(Game.class);
    }

    public List<Game> findAll() {
        Query query = session.getNamedQuery("game_findAll");
        return query.list();
    }

    public Game findByName(String gameName) {
        Query query = session.getNamedQuery("game_findByName");
        query.setParameter("name", gameName);
        return (Game) query.uniqueResult();
    }

    public List<Game> findAllGamesPlayedBy(Player player) {
        Query query = session.createSQLQuery(Game.findAllGamesPlayedBy).addEntity(Game.class);
        query = query.setParameter(0, player.getId());
        return query.list();
    }

    public List<Object[]> findAllScoreCountForEachGames() {
        Query query = session.createSQLQuery(Game.findAllScoreCountForEachGames);
        return query.list();
    }

}
