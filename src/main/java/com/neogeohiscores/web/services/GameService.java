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

package com.neogeohiscores.web.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;

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

    public Game getGameOfTheDay() {
        DateMidnight dateTime = new DateMidnight();
        List<Game> games = findAll();
        int num = (int) ((dateTime.getYear() * dateTime.getDayOfMonth() * dateTime.getMonthOfYear())%games.size());
        Game gameOfTheDay = games.get(num);
        return gameOfTheDay;
    }
}
