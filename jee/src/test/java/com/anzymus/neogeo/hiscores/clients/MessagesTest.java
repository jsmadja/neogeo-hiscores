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

package com.anzymus.neogeo.hiscores.clients;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Challenge;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class MessagesTest {

	@Test
	public void should_format_score() {
		Player player = new Player();
		String value = "12345";
		Game game = new Game("game");
		String level = "MVS";
		String pictureUrl = "http://www.image.com/id/1";
		Score score = new Score(value, player, level, game, pictureUrl);
		score.setMessage("(stage 5)");

		String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
				+ "[I](stage 5)[/I]\n" //
				+ "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
		assertEquals(expected, Messages.toMessage(score));
	}

	@Test
	public void should_format_score_with_all_clear_status() {
		Player player = new Player();
		String value = "12345";
		Game game = new Game("game");
		String level = "MVS";
		String pictureUrl = "http://www.image.com/id/1";
		Score score = new Score(value, player, level, game, pictureUrl);
		score.setMessage("(stage 5)");
		score.setAllClear(true);

		String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
				+ "[I](stage 5) - All clear![/I]\n" //
				+ "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
		assertEquals(expected, Messages.toMessage(score));
	}

	@Test
	public void should_format_score_with_stage() {
		Player player = new Player();
		String value = "12345";
		Game game = new Game("game");
		String level = "MVS";
		String pictureUrl = "http://www.image.com/id/1";
		Score score = new Score(value, player, level, game, pictureUrl);
		score.setMessage("message");
		score.setAllClear(true);
		score.setStage("5");

		String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
				+ "[I]message - stage 5 - All clear![/I]\n" //
				+ "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
		assertEquals(expected, Messages.toMessage(score));
	}

	@Test
	public void should_format_score_with_removing_stage_if_any() {
		Player player = new Player();
		String value = "12345";
		Game game = new Game("game");
		String level = "MVS";
		String pictureUrl = "http://www.image.com/id/1";
		Score score = new Score(value, player, level, game, pictureUrl);
		score.setMessage("message");
		score.setAllClear(true);
		score.setStage("StAgE 5");

		String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
				+ "[I]message - stage 5 - All clear![/I]\n" //
				+ "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
		assertEquals(expected, Messages.toMessage(score));
	}

	@Test
	public void should_format_challenge_message() {
		Game game = new Game("Fatal Fury");
		Challenge challenge = new Challenge();
		challenge.setGame(game);
		challenge.setPlayer2(new Player("neoforever"));
		challenge.setTitle("Premier défi");
		challenge.setDescription("Description du défi");
		String expected = "Here comes a new challenger!\n\n"
				+ "Je défie publiquement [B]neoforever[/B] sur [B]Fatal Fury[/B]\n\n" + "[I]Premier défi :[/I]\n"
				+ "[I]Description du défi[/I]\n\n" + "[SIZE=1]posté depuis [URL]www.neogeo-hiscores.com[/URL][/SIZE]";
		assertEquals(expected, Messages.toMessage(challenge));
	}
}
