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
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;

@Stateless
public class PlayerService {

    @PersistenceContext
    EntityManager em;

    public Player findByFullname(String fullname) {
        TypedQuery<Player> query = em.createNamedQuery("player_findByFullname", Player.class);
        query.setParameter("fullname", fullname);
        List<Player> player = query.getResultList();
        if (player.isEmpty()) {
            return null;
        }
        return player.get(0);
    }

    public List<Player> findAll() {
        TypedQuery<Player> query = em.createNamedQuery("player_findAll", Player.class);
        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Player store(Player player) {
        Player storedPlayer;
        if (player.getId() == null) {
            em.persist(player);
            storedPlayer = player;
        } else {
            storedPlayer = em.merge(player);
        }
        em.flush();
        return storedPlayer;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void unlockTitle(Player player, Title title) {
        Set<UnlockedTitle> unlockedTitles = player.getUnlockedTitles();
        UnlockedTitle unlockedTitle = new UnlockedTitle(player, title);
        unlockedTitles.add(unlockedTitle);
        store(player);
    }

	public Player findById(Long id) {
		return em.find(Player.class, id);
	}

}
