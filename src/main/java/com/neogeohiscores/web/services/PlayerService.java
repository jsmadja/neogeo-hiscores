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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;

import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;
import com.neogeohiscores.entities.Achievement;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Title;

public class PlayerService extends GenericService<Player> {

    @Inject
    private TitleService titleService;

    public PlayerService() {
        super(Player.class);
    }

    public Achievement getAchievementFor(Player player, Title title) {
        TitleUnlockingStrategy strategy = titleService.getStrategyByTitle(title);
        return strategy.getAchievementFor(player);
    }

    public List<Achievement> getAchievementsFor(Player player) {
        Map<Title, TitleUnlockingStrategy> StrategiesByTitle = titleService.findAllStrategies();
        Collection<TitleUnlockingStrategy> strategies = StrategiesByTitle.values();
        List<Achievement> achievements = new ArrayList<Achievement>();
        for (TitleUnlockingStrategy strategy : strategies) {
            achievements.add(strategy.getAchievementFor(player));
        }
        return achievements;
    }

    public Player findByFullname(String fullname) {
        Query query = session.getNamedQuery("player_findByFullname");
        query.setParameter("fullname", fullname);
        List<Player> player = query.list();
        if (player.isEmpty()) {
            return null;
        }
        return player.get(0);
    }

    public List<Player> findAll() {
        Query query = session.getNamedQuery("player_findAll");
        return query.list();
    }

    public long getNumberOfPlayers() {
        return (Long) session.getNamedQuery("player_getNumberOfPlayers").uniqueResult();
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM Player");
        query.executeUpdate();
    }

}
