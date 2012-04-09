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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.service.halloffame.HallOfOneCreditService;
import com.anzymus.neogeo.hiscores.webservice.AdministrationWebService;

public abstract class AbstractTest {

	protected static Context namingContext;

	protected static PlayerService playerService;
	protected static ScoreService scoreService;
	protected static GameService gameService;
	protected static TitleService titleService;
	protected static TitleUnlockingService titleUnlockingService;
	protected static TitleRelockingService titleRelockingService;
	protected static AdministrationWebService administrationWebService;
	protected static HallOfOneCreditService hallOfOneCreditService;
	protected static ChallengeService challengeService;

	static {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(EJBContainer.MODULES, new File("target/classes"));
			EJBContainer container = EJBContainer.createEJBContainer(properties);

			namingContext = container.getContext();

			playerService = (PlayerService) lookup("PlayerService");
			scoreService = (ScoreService) lookup("ScoreService");
			gameService = (GameService) lookup("GameService");
			titleService = (TitleService) lookup("TitleService");
			titleUnlockingService = (TitleUnlockingService) lookup("TitleUnlockingService");
			titleRelockingService = (TitleRelockingService) lookup("TitleRelockingService");
			hallOfOneCreditService = (HallOfOneCreditService) lookup("HallOfOneCreditService");
			challengeService = (ChallengeService) lookup("ChallengeService");

			administrationWebService = new AdministrationWebService();
			administrationWebService.setPlayerService(playerService);
			administrationWebService.setScoreService(scoreService);
			administrationWebService.setTitleUnlockingService(titleUnlockingService);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Before
	public void init() {
		titleUnlockingService.deleteAll();
		titleRelockingService.deleteAll();
		titleService.deleteAll();
		scoreService.deleteAll();
		challengeService.deleteAll();
		playerService.deleteAll();
	}

	private static Object lookup(String key) throws NamingException {
		return namingContext.lookup("java:global/classes/" + key);
	}

	protected Score createScore(Player player, Game game) {
		String value = RandomStringUtils.randomNumeric(7);
		String level = "MVS";
		String pictureUrl = "http://";
		Score score = new Score(value, player, level, game, pictureUrl);
		return scoreService.store(score);
	}

	protected Game createGame() {
		String name = RandomStringUtils.randomAlphabetic(10);
		Game game = new Game(name);
		return gameService.store(game);
	}

	protected Player createPlayer() {
		String fullname = RandomStringUtils.randomAlphabetic(10);
		Player player = new Player(fullname);
		return playerService.store(player);
	}

	protected Title createTitle() {
		Title title = new Title();
		title.setClassname("com.anzymus.neogeo.hiscores.success.FirstScoreTitleStrategy");
		title.setDescription("A title");
		title.setLabel("First score");
		title.setPosition(1L);
		return titleService.store(title);
	}

}
