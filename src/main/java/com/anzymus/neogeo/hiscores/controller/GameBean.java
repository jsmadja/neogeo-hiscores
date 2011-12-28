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

import static com.anzymus.neogeo.hiscores.common.IntegerToRank.getOrdinalFor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class GameBean {

	private static final int MIN_SCORE_TO_SHOW = 10;

	@EJB
	ScoreService scoreService;

	@EJB
	GameService gameService;

	@ManagedProperty(value = "#{param.id}")
	private String id;

	private String name;

	private Scores scores;

	private Long postId;

	private static Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();

	private List<LevelItem> levelItems = new ArrayList<LevelItem>();

	@PostConstruct
	public void init() {
		long gameId = Long.parseLong(id);
		Game game = gameService.findById(gameId);
		name = game.getName();
		postId = game.getPostId();
		scores = scoreService.findAllByGame(game);
		loadLevelItems();
	}

	private void loadLevelItems() {
		for (String level : Levels.list()) {
			List<Score> scoreList = scores.getScoresByLevel(level);
			if (!scoreList.isEmpty()) {
				Collections.sort(scoreList, sortScoreByValueDesc);
				List<ScoreItem> scoreItems = createScoreItems(scoreList);
				LevelItem levelItem = new LevelItem(level);
				levelItem.setScoreItems(scoreItems);
				levelItems.add(levelItem);
			}
		}
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<LevelItem> getLevels() {
		return levelItems;
	}

	private List<ScoreItem> createScoreItems(List<Score> scores) {
		Score oldScore = null;
		String oldRank = null;
		List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();
		int numScoreToShow = scores.size() > MIN_SCORE_TO_SHOW ? scores.size() : MIN_SCORE_TO_SHOW;
		for (int i = 0; i < numScoreToShow; i++) {
			String rank;
			ScoreItem scoreItem = new ScoreItem();
			if (i < scores.size()) {
				Score score = scores.get(i);
				scoreItem.setValue(score.getValue());
				scoreItem.setPlayer(score.getPlayer().getFullname());
				scoreItem.setPictureUrl(score.getPictureUrl());
				scoreItem.setId(score.getId());
				scoreItem.setMessage(score.getMessage());
				scoreItem.setStage(score.getStage());
				scoreItem.setDate(score.getCreationDate());
				scoreItem.setAllClear(score.getAllClear());
				if (oldScore != null && oldScore.getValue().equals(score.getValue())) {
					rank = oldRank;
				} else {
					rank = getOrdinalFor(i + 1);
				}
				oldScore = score;
			} else {
				scoreItem.setValue("");
				scoreItem.setPlayer("");
				oldScore = null;
				rank = getOrdinalFor(i + 1);
			}
			scoreItem.setRank(rank);
			scoreItems.add(scoreItem);
			oldRank = rank;
		}
		return scoreItems;
	}

	public Long getPostId() {
		return postId;
	}

}
