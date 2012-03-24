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

import static java.text.MessageFormat.format;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.RelockedTitle;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TitleRelockingService {

	private static final Logger LOG = LoggerFactory.getLogger(TitleRelockingService.class);

	@PersistenceContext
	EntityManager em;

	@EJB
	private PlayerService playerService;

	@EJB
	private TitleUnlockingService titleUnlockingService;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RelockedTitle store(RelockedTitle relockedTitle) {
		RelockedTitle storedRelockedTitle;
		if (relockedTitle.getId() == null) {
			em.persist(relockedTitle);
			storedRelockedTitle = relockedTitle;
		} else {
			storedRelockedTitle = em.merge(relockedTitle);
		}
		em.flush();
		return storedRelockedTitle;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void relockTitles(Score relockerScore) {
		Player relockerPlayer = relockerScore.getPlayer();
		List<Player> players = playerService.findAll();
		for (Player player : players) {
			if (!player.equals(relockerPlayer)) {
				relockTitles(relockerPlayer, relockerScore, player);
			}
		}
	}

	private void relockTitles(Player relockerPlayer, Score relockerScore, Player player) {
		Set<UnlockedTitle> unlockedTitles = player.getUnlockedTitles();
		for (UnlockedTitle unlockedTitle : unlockedTitles) {
			Title title = unlockedTitle.getTitle();
			if (isRelockable(title, player)) {
				RelockedTitle relockedTitle = createRelockedTitle(relockerScore, player, title);
				player.getRelockedTitles().add(relockedTitle);
				unlockedTitles.remove(unlockedTitle);
				LOG.info(format("The score on {0} posted by {1} relocked the title {2} of player {3}",
						relockerScore.getGame(), relockerPlayer, title.getLabel(), player));
			}
		}
		playerService.store(player);
	}

	private RelockedTitle createRelockedTitle(Score relockerScore, Player player, Title title) {
		RelockedTitle relockedTitle = new RelockedTitle();
		relockedTitle.setPlayer(player);
		relockedTitle.setRelockerScore(relockerScore);
		relockedTitle.setTitle(title);
		relockedTitle = store(relockedTitle);
		return relockedTitle;
	}

	private boolean isRelockable(Title title, Player player) {
		return titleUnlockingService.isRelockable(title, player);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM RelockedTitle");
		query.executeUpdate();
	}

}
