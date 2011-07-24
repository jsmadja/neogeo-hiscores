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

import java.util.List;
import javax.ejb.Stateless;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScoreService {
    
    @PersistenceContext
    EntityManager em;
    
    public static final int MAX_SCORES_TO_RETURN = 20;

    public Scores findAllByGame(Game game) {
        TypedQuery<Score> query = em.createNamedQuery("score_findAllByGame", Score.class);
        query.setParameter("game", game);
        List<Score> scores = query.getResultList();
        return toScores(scores);
    }

    public Scores findAllByPlayer(Player player) {
        TypedQuery<Score> query = em.createNamedQuery("score_findAllByPlayer", Score.class);
        query.setParameter("player", player);
        List<Score> scores = query.getResultList();
        return toScores(scores);
    }

    public Scores findAll() {
        TypedQuery<Score> query = em.createNamedQuery("score_findAll", Score.class);
        List<Score> scores = query.getResultList();
        return toScores(scores);
    }
    
    public List<Score> findLastScoresOrderByDateDesc() {
        TypedQuery<Score> query = em.createNamedQuery("score_findAllOrderByDateDesc", Score.class);
        query.setMaxResults(MAX_SCORES_TO_RETURN);
        return query.getResultList();
    }

    public Score findById(long id) {
        return em.find(Score.class, id);
    }

    private Scores toScores(List<Score> scoreList) {
        Scores scores = new Scores();
        if (scoreList != null) {
            for(Score score:scoreList) {
                scores.add(score);
            }
        }
        return scores;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Score store(Score score) {
        Score storedScore;
        if (score.getId() == null) {
            em.persist(score);
            storedScore = score;
        } else {
            storedScore = em.merge(score);
        }
        em.flush();
        return storedScore;
    }

}
