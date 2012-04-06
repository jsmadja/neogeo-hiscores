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

package com.anzymus.neogeo.hiscores.webservice;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import com.anzymus.neogeo.hiscores.service.TitleRelockingService;
import com.anzymus.neogeo.hiscores.service.TitleUnlockingService;

@WebService
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AdministrationWebService {

	private @EJB
	GameService gameService;

	@EJB
	ScoreService scoreService;

	@EJB
	PlayerService playerService;

	@EJB
	TitleUnlockingService titleUnlockingService;

	@EJB
	TitleRelockingService titleRelockingService;

	private static final Logger LOG = LoggerFactory.getLogger(AdministrationWebService.class);

	@WebMethod
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteScore(Long scoreId) {
		scoreService.delete(scoreId);
		refreshTitles();
	}

	@WebMethod
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void refreshTitles() {
		unlockNewTitles();
		relockTitles();
	}

	private void unlockNewTitles() {
		List<Player> players = playerService.findAll();
		for (Player player : players) {
			titleUnlockingService.searchUnlockedTitlesFor(player);
		}
	}

	private void relockTitles() {
		List<UnlockedTitle> unlockedTitles = titleUnlockingService.findAll();
		for (UnlockedTitle unlockedTitle : unlockedTitles) {
			if (titleUnlockingService.isRelockable(unlockedTitle)) {
				titleUnlockingService.remove(unlockedTitle);
				LOG.info("Title " + unlockedTitle.getTitle().getLabel() + " has been removed from player " + unlockedTitle.getPlayer());
			}
		}
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	public void setTitleUnlockingService(TitleUnlockingService titleUnlockingService) {
		this.titleUnlockingService = titleUnlockingService;
	}

}
