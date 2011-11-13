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

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;
import com.google.common.annotations.VisibleForTesting;

@Stateless
public class TitleUnlockingService {

    @EJB
    TitleService titleService;

    @EJB
    PlayerService playerService;

    @VisibleForTesting
    Map<Title, TitleUnlockingStrategy> strategiesByTitle;

    @PersistenceContext
    EntityManager em;

    private static final int MAX_UNLOCKED_TITLES_TO_RETURN = 10;

    @PostConstruct
    public void init() {
        strategiesByTitle = titleService.findAllStrategies();
    }

    public void searchUnlockedTitlesFor(Player player) {
        strategiesByTitle = titleService.findAllStrategies();
        for (Title title : strategiesByTitle.keySet()) {
            TitleUnlockingStrategy strategy = strategiesByTitle.get(title);
            if (isUnlockable(player, title, strategy)) {
                playerService.unlockTitle(player, title);
            }
        }
    }

    private boolean isUnlockable(Player player, Title title, TitleUnlockingStrategy strategy) {
        return player.hasNotUnlocked(title) && strategy.isUnlocked(player);
    }

    public List<UnlockedTitle> findLastUnlockedTitlesOrderByDateDesc() {
        TypedQuery<UnlockedTitle> query = em.createNamedQuery("unlockedTitle_findLastUnlockedTitlesOrderByDateDesc",
                UnlockedTitle.class);
        query.setMaxResults(MAX_UNLOCKED_TITLES_TO_RETURN);
        return query.getResultList();
    }

    public List<Player> findPlayersOrderByNumUnlockedTitles() {
        TypedQuery<Player> query = em.createNamedQuery("unlockedTitle_findPlayersOrderByNumUnlockedTitles",
                Player.class);
        return query.getResultList();
    }
}
