/*
 * Copyright 2012 juliensmadja.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anzymus.neogeo.hiscores.controller;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ChallengeableGame {

	private Game game;
	private Score player1Score;
	private Score player2Score;

	public ChallengeableGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Score getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(Score player1Score) {
		this.player1Score = player1Score;
	}

	public Score getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(Score player2Score) {
		this.player2Score = player2Score;
	}

}
