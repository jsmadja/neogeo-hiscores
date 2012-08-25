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

package com.neogeohiscores.web.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;

import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Scores;
import com.neogeohiscores.entities.Title;
import com.neogeohiscores.web.services.halloffame.NgfPointCalculator;
import com.neogeohiscores.web.services.halloffame.PointCalculator;

public class TitleService extends GenericService<Title> {

    private static final Logger LOG = Logger.getLogger(TitleService.class.getName());

    @Inject
    ScoreService scoreService;

    private @Inject
    GameService gameService;

    private Map<Title, TitleUnlockingStrategy> strategies;

    public TitleService() {
        super(Title.class);
    }

    public long getNumScoresByPlayer(Player player) {
        Query query = session.getNamedQuery("getNumScoresByPlayer");
        query.setParameter("player", player);
        return (Long) query.uniqueResult();
    }

    @PostConstruct
    public void init() {
        this.strategies = findAllStrategies();
    }

    public Map<Title, TitleUnlockingStrategy> findAllStrategies() {
        Map<Title, TitleUnlockingStrategy> strategies = new HashMap<Title, TitleUnlockingStrategy>();
        Query query = session.getNamedQuery("findAllStrategies");
        for (Title title : new ArrayList<Title>(query.list())) {
            try {
                String classname = title.getClassname();
                TitleUnlockingStrategy strategy = (TitleUnlockingStrategy) Class.forName(classname).newInstance();
                strategy.initialize(this);
                strategy.setTitle(title);
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

    public boolean hasScoreInGame(Player player, String game) {
        Query query = session.getNamedQuery("hasScoreInGame");
        query.setParameter("player", player);
        query.setParameter("game", game);
        return (Long) query.uniqueResult() > 0;
    }

    public void addStrategy(Title title, TitleUnlockingStrategy titleUnlockingStrategy) {
        title.setClassname(titleUnlockingStrategy.getClass().getName());
        store(title);
    }

    public int getNumAllClearsByPlayer(Player player) {
        Query query = session.getNamedQuery("getNumAllClearsByPlayer");
        query.setParameter("player", player);
        return query.list().size();
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM Title");
        query.executeUpdate();
    }

    public List<Scores> getScoresByGameGenre(String genre) {
        List<Scores> scores = new ArrayList<Scores>();
        List<Game> games = gameService.findGamesByGenre(genre);
        for (Game game : games) {
            scores.add(scoreService.findAllByGame(game));
        }
        return scores;
    }

    public double getAverageScoreFor(Player player) {
        String level = "MVS";
        double points = 0;
        double contributions = 0;
        List<Game> games = gameService.findAllGamesPlayedBy(player);
        for (Game game : games) {
            Scores scores = scoreService.findAllByGame(game, level);
            List<Score> scoresByLevel = scores.asSortedList();
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

    public TitleUnlockingStrategy getStrategyByTitle(Title title) {
        return this.strategies.get(title);
    }
}
