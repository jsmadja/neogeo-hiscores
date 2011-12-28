package com.anzymus.neogeo.hiscores.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
public class MonthlyContestBean {

	private static final int MAX_GAMES_IN_CONTEST = 3;

	@EJB
	private GameService gameService;

	@EJB
	private ScoreService scoreService;

	private List<GameItem> mostScoredGames;

	private List<GameItem> gameItems;

	@PostConstruct
	public void init() {
		List<GameItem> gameItems = new ArrayList<GameItem>();
		List<Game> playedGamesThisMonth = gameService.findAllPlayedGamesThisMonth();
		for (Game game : playedGamesThisMonth) {
			GameItem gameItem = new GameItem(game);
			Scores scores = scoreService.findAllByGameThisMonth(game);
			List<ScoreItem> scoreItems = ScoreItems.createScoreItems(scores.asSortedList(), 1);
			removePlayerDuplicates(scoreItems);
			gameItem.setScores(scoreItems);
			gameItems.add(gameItem);
		}
		this.gameItems = keepOnlyMostScoredGames(gameItems);
	}

	public String getMonth() {
		return new SimpleDateFormat("MMMMM", Locale.UK).format(new Date());
	}

	public int getYear() {
		return new DateTime().getYear();
	}

	public List<GameItem> getGames() {
		return gameItems;
	}

	private List<GameItem> keepOnlyMostScoredGames(List<GameItem> gameItems) {
		Collections.sort(gameItems, new Comparator<GameItem>() {
			@Override
			public int compare(GameItem gameItem1, GameItem gameItem2) {
				return gameItem2.getScores().size() - gameItem1.getScores().size();
			}
		});
		int subListSize = gameItems.size() > MAX_GAMES_IN_CONTEST ? MAX_GAMES_IN_CONTEST : gameItems.size();
		mostScoredGames = gameItems.subList(0, subListSize);
		return gameItems.subList(subListSize, gameItems.size());
	}

	public List<GameItem> getMostScoredGames() {
		return mostScoredGames;
	}

	private void removePlayerDuplicates(List<ScoreItem> scoreItems) {
		Set<String> players = new HashSet<String>();
		List<ScoreItem> scoreItemsToRemove = new ArrayList<ScoreItem>();
		for (ScoreItem scoreItem : scoreItems) {
			String player = scoreItem.getPlayer();
			if (players.contains(player)) {
				scoreItemsToRemove.add(scoreItem);
			}
			players.add(player);
		}
		for (ScoreItem scoreItem : scoreItemsToRemove) {
			scoreItems.remove(scoreItem);
		}
	}

}
