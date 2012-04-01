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
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.RelockedTitle;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TitleUnlockingService extends GenericService<UnlockedTitle> {

	@EJB
	TitleService titleService;

	@EJB
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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
		TypedQuery<UnlockedTitle> query = em.createNamedQuery("findLastUnlockedTitlesOrderByDateDesc", UnlockedTitle.class);
		query.setMaxResults(MAX_UNLOCKED_TITLES_TO_RETURN);
		return query.getResultList();
	}

	public List<RelockedTitle> findLastRelockedTitlesOrderByDateDesc() {
		TypedQuery<RelockedTitle> query = em.createNamedQuery("findLastRelockedTitlesOrderByDateDesc", RelockedTitle.class);
		query.setMaxResults(MAX_RELOCKED_TITLES_TO_RETURN);
		return query.getResultList();
	}

	public List<Player> findPlayersOrderByNumUnlockedTitles() {
		String sql = "SELECT p.* FROM UNLOCKED_TITLE ut, PLAYER p WHERE ut.player_id = p.id GROUP BY ut.player_id ORDER BY COUNT(ut.id) DESC";
		Query query = em.createNativeQuery(sql, Player.class);
		return query.getResultList();
	}

	public boolean isRelockable(Title title, Player player) {
		return !strategiesByTitle.get(title).isUnlockable(player);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM UnlockedTitle");
		query.executeUpdate();
	}

	public List<UnlockedTitle> findAll() {
		TypedQuery<UnlockedTitle> query = em.createQuery("SELECT ut FROM UnlockedTitle ut", UnlockedTitle.class);
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(UnlockedTitle unlockedTitle) {
		unlockedTitle = findById(unlockedTitle.getId());
		unlockedTitle.getPlayer().getUnlockedTitles().remove(unlockedTitle);
	}

}
