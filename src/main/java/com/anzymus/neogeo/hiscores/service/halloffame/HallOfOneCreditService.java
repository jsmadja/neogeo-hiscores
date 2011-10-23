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

package com.anzymus.neogeo.hiscores.service.halloffame;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.PlayerService;

@Stateless
public class HallOfOneCreditService {

    @PersistenceContext
    EntityManager em;
    @EJB
    PlayerService playerService;

    private static final String ONE_CREDIT_QUERY = "SELECT s.PLAYER_ID, COUNT(s.PLAYER_ID) FROM SCORE s, PLAYER p WHERE s.ALL_CLEAR = 1 AND s.PLAYER_ID=p.ID GROUP BY PLAYER_ID ORDER BY COUNT(s.PLAYER_ID) DESC, p.FULLNAME ASC";
    
    public List<Player> getPlayersOrderByAllClearCount() {
        List<Player> players = new ArrayList<Player>();
        Query query = em.createNativeQuery(ONE_CREDIT_QUERY);
        List<Object[]> results = query.getResultList();
        for (Object[] result:results) {
            Long playerId = (Long) result[0];
            Long points = (Long) result[1];
            Player player = playerService.findById(playerId);
            player.setPoints(points.intValue());
            player.setContribution(points.intValue());
            players.add(player);
        }
        return players;
    }
}
