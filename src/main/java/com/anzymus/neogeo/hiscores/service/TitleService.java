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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.service.halloffame.NgfPointCalculator;
import com.anzymus.neogeo.hiscores.service.halloffame.PointCalculator;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TitleService {

	private static final Logger LOG = Logger.getLogger(TitleService.class.getName());

	@PersistenceContext
	EntityManager em;

	@EJB
	ScoreService scoreService;

	@EJB
	GameService gameService;

	public long getNumScoresByPlayer(Player player) {
		Query query = em.createNamedQuery("getNumScoresByPlayer");
		query.setParameter("player", player);
		return (Long) query.getSingleResult();
	}

	public Map<Title, TitleUnlockingStrategy> findAllStrategies() {
		Map<Title, TitleUnlockingStrategy> strategies = new HashMap<Title, TitleUnlockingStrategy>();
		TypedQuery<Title> query = em.createNamedQuery("findAllStrategies", Title.class);
		for (Title title : query.getResultList()) {
			try {
				String classname = title.getClassname();
				TitleUnlockingStrategy strategy = (TitleUnlockingStrategy) Class.forName(classname).newInstance();
				strategy.initialize(this);
				strategies.put(title, strategy);
			} catch (ClassNotFoundException e) {
				LOG.log(Level.WARNING, "Can't create strategy from title: " + title, e);
			} catch (InstantiationException e) {
				LOG.log(Level.WARNING, "Can't create strategy from title: " + title, e);
			} catch (IllegalAccessException e) {
				LOG.log(Level.WARNING, "Can't create strategy from title: " + title, e);
			}
		}
		return strategies;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Title store(Title title) {
		Title storedTitle;
		if (title.getId() == null) {
			em.persist(title);
			storedTitle = title;
		} else {
			storedTitle = em.merge(title);
		}
		em.flush();
		return storedTitle;
	}

	public boolean hasScoreInGame(Player player, String game) {
		Query query = em.createNamedQuery("hasScoreInGame");
		query.setParameter("player", player);
		query.setParameter("game", game);
		return (Long) query.getSingleResult() > 0;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addStrategy(Title title, TitleUnlockingStrategy titleUnlockingStrategy) {
		title.setClassname(titleUnlockingStrategy.getClass().getName());
		store(title);
	}

	public int getNumAllClearsByPlayer(Player player) {
		Query query = em.createNamedQuery("getNumAllClearsByPlayer");
		query.setParameter("player", player);
		return query.getResultList().size();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Title");
		query.executeUpdate();
	}

	public List<Scores> getScoresByGameGenres(String[] genres) {
		List<Scores> scores = new ArrayList<Scores>();
		List<Game> games = gameService.findGamesByGenres(genres);
		for (Game game : games) {
			scores.add(scoreService.findAllByGame(game));
		}
		return scores;
	}

	public double getAverageScoreFor(Player player) {
		String level = "MVS";
		double points = 0;
		double contributions = 0;
		List<Game> games = gameService.findAll();
		for (Game game : games) {
			Scores scores = scoreService.findAllByGame(game);
			List<Score> scoresByLevel = scores.getScoresByLevel(level);
			int maxPoints = scoresByLevel.size() >= 10 ? 10 : scoresByLevel.size();
			Collections.sort(scoresByLevel, sortScoreByValueDesc);
			Score oldScore = null;
			int oldPoint = 0;
			for (int i = 0; i < scoresByLevel.size() && i < 8; i++) {
				Score score = scoresByLevel.get(i);
				int point = pointCalculator.getPointsByIndex(i, maxPoints);
				if (oldScore != null && score.getValue().equals(oldScore.getValue())) {
					point = oldPoint;
				}
				if (score.getPlayer().equals(player)) {
				    points += point;
				    contributions++;
				}
				oldScore = score;
				oldPoint = point;
				
			}
		}
		return points / contributions;
	}

	private Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();
	private PointCalculator pointCalculator = new NgfPointCalculator();

}
