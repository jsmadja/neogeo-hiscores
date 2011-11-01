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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

public class HallOfFameServiceTest {

	@Mock
	ScoreService scoreService;

	@Mock
	GameService gameService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_handle_ex_aequo() {
		HallOfFameService hallOfFameService = new HallOfFameService();
		hallOfFameService.scoreService = scoreService;
		hallOfFameService.gameService = gameService;

		Game fatalFury = new Game("Fatal Fury");
		List<Game> games = Arrays.asList(fatalFury);
		when(gameService.findAll()).thenReturn(games);

		Scores scores = new Scores();
		scores.add(new Score("5", new Player("player4"), "MVS", fatalFury, ""));
		scores.add(new Score("7", new Player("player2"), "MVS", fatalFury, ""));
		scores.add(new Score("7", new Player("player3"), "MVS", fatalFury, ""));
		scores.add(new Score("10", new Player("player1"), "MVS", fatalFury, ""));
		scores.add(new Score("4", new Player("player5"), "MVS", fatalFury, ""));
		scores.add(new Score("3", new Player("player6"), "MVS", fatalFury, ""));
		scores.add(new Score("2", new Player("player7"), "MVS", fatalFury, ""));
		scores.add(new Score("1", new Player("player8"), "MVS", fatalFury, ""));
		scores.add(new Score("0", new Player("player9"), "MVS", fatalFury, ""));

		when(scoreService.findAllByGame(fatalFury)).thenReturn(scores);

		List<Player> players = hallOfFameService.getPlayersOrderByRank("MVS");

		assertEquals(10, players.get(0).getPoints());
		assertEquals(8, players.get(1).getPoints());
		assertEquals(8, players.get(2).getPoints());
		assertEquals(5, players.get(3).getPoints());
		assertEquals(4, players.get(4).getPoints());
		assertEquals(3, players.get(5).getPoints());
		assertEquals(2, players.get(6).getPoints());
		assertEquals(1, players.get(7).getPoints());
	}

}
