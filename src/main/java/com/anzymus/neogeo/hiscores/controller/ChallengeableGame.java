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

import java.io.Serializable;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ChallengeableGame implements Serializable {

	private static final long serialVersionUID = -3681065486951796856L;

	private Game game;
	private Score player1Score;
	private Score player2Score;
	private boolean score1Greater;
	private boolean score2Greater;

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

	public boolean getScore1Greater() {
		return score1Greater;
	}

	public void setScore1Greater(boolean score1Greater) {
		this.score1Greater = score1Greater;
	}

	public boolean getScore2Greater() {
		return score2Greater;
	}

	public void setScore2Greater(boolean score2Greater) {
		this.score2Greater = score2Greater;
	}

}
