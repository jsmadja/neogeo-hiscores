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

package com.neogeohiscores.web.services.halloffame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.neogeohiscores.comparator.PlayerSortedByPointsDescComparator;
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Scores;
import com.neogeohiscores.web.services.GameService;
import com.neogeohiscores.web.services.ScoreService;

public class HallOfFameService {

    @Inject
    ScoreService scoreService;

    @Inject
    GameService gameService;
    private static Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();

    private Comparator<Player> sortByPlayerPointsComparator = new PlayerSortedByPointsDescComparator();

    public List<Player> getPlayersOrderByRank(String level) {
        PointCalculator pointCalculator = new NgfPointCalculator();
        return createPlayerList(level, pointCalculator);
    }

    private List<Player> createPlayerList(String level, PointCalculator pointCalculator) {
        Map<String, Player> players = new HashMap<String, Player>();
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
                Player scorePlayer = score.getPlayer();
                String fullname = scorePlayer.getFullname();
                Player player = players.get(fullname);
                if (player == null) {
                    player = new Player(fullname);
                    player.setId(scorePlayer.getId());
                    players.put(fullname, player);
                }
                player.setPoints(player.getPoints() + point);
                player.setContribution(player.getContribution() + 1);
                oldScore = score;
                oldPoint = point;
            }
        }

        List<Player> sortedPlayers = new ArrayList<Player>();
        sortedPlayers.addAll(players.values());
        Collections.sort(sortedPlayers, sortByPlayerPointsComparator);
        return sortedPlayers;
    }

}
