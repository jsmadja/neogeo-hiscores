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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.joda.time.DateTime;

import com.neogeohiscores.entities.Challenge;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.exception.ChallengeNotCreatedException;

public class ChallengeService extends GenericService<Challenge> {

    @Inject
    GameService gameService;

    public ChallengeService() {
        super(Challenge.class);
    }

    public Collection<Player> findPlayersToChallenge(Player player) {
        Long playerId = player.getId();
        Query query = session.createSQLQuery("SELECT * FROM PLAYER WHERE ID IN(SELECT s.PLAYER_ID FROM SCORE s WHERE s.PLAYER_ID != " + playerId
                + " AND s.GAME_ID IN (SELECT GAME_ID FROM SCORE s WHERE s.PLAYER_ID = " + playerId + ")) ORDER BY FULLNAME");
        return query.list();
    }

    public Challenge createChallenge(Player player1, Player player2, Game game, String title, String description) throws ChallengeNotCreatedException {
        if (!areChallengeable(player1, player2, game)) {
            throw new ChallengeNotCreatedException(player1.getFullname() + " can't challenge " + player2.getFullname());
        }
        Challenge challenge = new Challenge();
        challenge.setPlayer1(player1);
        challenge.setPlayer2(player2);
        challenge.setGame(game);
        challenge.setTitle(title);
        challenge.setDescription(description);
        DateTime oneMonthLater = new DateTime().plusMonths(1);
        Date finishDate = oneMonthLater.toDate();
        challenge.setFinishDate(finishDate);
        session.persist(challenge);
        return challenge;
    }

    private boolean areChallengeable(Player player1, Player player2, Game game) {
        List<Game> gamesPlayedByPlayer1 = gameService.findAllGamesPlayedBy(player1);
        if (gamesPlayedByPlayer1.isEmpty()) {
            return false;
        }
        List<Game> gamesPlayedByPlayer2 = gameService.findAllGamesPlayedBy(player2);
        if (gamesPlayedByPlayer2.isEmpty()) {
            return false;
        }
        return true;
    }

    public Collection<Challenge> findAllActiveChallenges() {
        Query query = session.getNamedQuery("challenge_findAllActiveChallenges");
        query.setParameter("finishDate", new Date());
        return query.list();
    }

    public Collection<Game> findCommonGamesFor(Player player1, Player player2) {
        List<Game> player1games = gameService.findAllGamesPlayedBy(player1);
        List<Game> player2games = gameService.findAllGamesPlayedBy(player2);
        Collection<Game> commonsGames = new TreeSet<Game>();
        for (Game game : player1games) {
            if (player2games.contains(game)) {
                commonsGames.add(game);
            }
        }
        return commonsGames;
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM Challenge");
        query.executeUpdate();
    }

}
