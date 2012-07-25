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

package com.neogeohiscores.web.services.halloffame;

import com.neogeohiscores.entities.Player;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class HallOfOneCreditService {

    @Inject
    Session em;

    private static final String ONE_CREDIT_QUERY = "SELECT s.PLAYER_ID, COUNT(DISTINCT s.GAME_ID) FROM SCORE s, PLAYER p WHERE s.ALL_CLEAR = 1 AND s.PLAYER_ID=p.ID AND s.LEVEL_LABEL = \"MVS\" GROUP BY PLAYER_ID ORDER BY COUNT(DISTINCT s.GAME_ID) DESC, p.FULLNAME ASC";

    public List<Player> getPlayersOrderByAllClearCount() {
        List<Player> players = new ArrayList<Player>();
        Query query = em.createSQLQuery(ONE_CREDIT_QUERY);
        List<Object[]> results = query.list();
        for (Object[] result : results) {
            Long playerId = ((BigInteger) result[0]).longValue();
            Long points = ((BigInteger) result[1]).longValue();
            Player player = (Player) em.load(Player.class, playerId);
            player.setPoints(points.intValue());
            player.setContribution(points.intValue());
            players.add(player);
        }
        return players;
    }
}
