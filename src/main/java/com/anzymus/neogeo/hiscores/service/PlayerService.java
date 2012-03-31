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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.anzymus.neogeo.hiscores.domain.Achievement;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PlayerService extends GenericService<Player> {

	@EJB
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

	public long getNumberOfPlayers() {
		return (Long) em.createNamedQuery("player_getNumberOfPlayers").getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Player");
		query.executeUpdate();
	}

}
