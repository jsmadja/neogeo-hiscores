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

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;

import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.RelockedTitle;
import com.neogeohiscores.entities.Title;
import com.neogeohiscores.entities.UnlockedTitle;

public class TitleUnlockingService extends GenericService<UnlockedTitle> {

    @Inject
    TitleService titleService;

    @Inject
    PlayerService playerService;

    Map<Title, TitleUnlockingStrategy> strategiesByTitle;

    private static final int MAX_UNLOCKED_TITLES_TO_RETURN = 10;

    private static final int MAX_RELOCKED_TITLES_TO_RETURN = 20;

    public TitleUnlockingService() {
        super(UnlockedTitle.class);
    }

    @PostConstruct
    public void init() {
        strategiesByTitle = titleService.findAllStrategies();
    }

    public void searchUnlockedTitlesFor(Player player) {
        strategiesByTitle = titleService.findAllStrategies();
        for (Title title : strategiesByTitle.keySet()) {
            TitleUnlockingStrategy strategy = strategiesByTitle.get(title);
            if (isUnlockable(player, title, strategy)) {
                Set<UnlockedTitle> unlockedTitles = player.getUnlockedTitles();
                UnlockedTitle unlockedTitle = new UnlockedTitle(player, title);
                unlockedTitles.add(unlockedTitle);
            }
        }
        playerService.merge(player);
    }

    private boolean isUnlockable(Player player, Title title, TitleUnlockingStrategy strategy) {
        return player.hasNotUnlocked(title) && strategy.isUnlockable(player);
    }

    public boolean isRelockable(UnlockedTitle unlockedTitle) {
        Player player = unlockedTitle.getPlayer();
        Title title = unlockedTitle.getTitle();
        TitleUnlockingStrategy strategy = strategiesByTitle.get(title);
        return !strategy.isUnlockable(player);
    }

    public List<UnlockedTitle> findLastUnlockedTitlesOrderByDateDesc() {
        Query query = session.getNamedQuery("findLastUnlockedTitlesOrderByDateDesc");
        query.setMaxResults(MAX_UNLOCKED_TITLES_TO_RETURN);
        return query.list();
    }

    public List<RelockedTitle> findLastRelockedTitlesOrderByDateDesc() {
        Query query = session.getNamedQuery("findLastRelockedTitlesOrderByDateDesc");
        query.setMaxResults(MAX_RELOCKED_TITLES_TO_RETURN);
        return query.list();
    }

    public List<Player> findPlayersOrderByNumUnlockedTitles() {
        String sql = "SELECT p.* FROM UNLOCKED_TITLE ut, PLAYER p WHERE ut.player_id = p.id GROUP BY ut.player_id ORDER BY COUNT(ut.id) DESC";
        Query query = session.createSQLQuery(sql);
        return query.list();
    }

    public boolean isRelockable(Title title, Player player) {
        return !strategiesByTitle.get(title).isUnlockable(player);
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM UnlockedTitle");
        query.executeUpdate();
    }

    public List<UnlockedTitle> findAll() {
        Query query = session.createQuery("SELECT ut FROM UnlockedTitle ut");
        return query.list();
    }

    public void remove(UnlockedTitle unlockedTitle) {
        unlockedTitle = findById(unlockedTitle.getId());
        unlockedTitle.getPlayer().getUnlockedTitles().remove(unlockedTitle);
    }

}
