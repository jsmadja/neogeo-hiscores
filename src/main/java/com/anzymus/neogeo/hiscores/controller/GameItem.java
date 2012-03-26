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

package com.anzymus.neogeo.hiscores.controller;

import java.util.List;

import com.anzymus.neogeo.hiscores.domain.Game;

public class GameItem {

	private String name;
	private long id;
	private long count;
	private Game game;
	private String genre;
	private List<ScoreItem> scores;

	public GameItem(String name, long id) {
		this.name = name;
		this.id = id;
	}

	GameItem(String name, long id, long count) {
		this(name, id);
		this.count = count;
	}

	GameItem(String name, long id, long count, String genre) {
		this(name, id);
		this.count = count;
		this.setGenre(genre);
	}

	public GameItem(Game game) {
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public long getCount() {
		return count;
	}

	public Game getGame() {
		return game;
	}

	public void setScores(List<ScoreItem> scores) {
		this.scores = scores;
	}

	public List<ScoreItem> getScores() {
		return scores;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}
}
