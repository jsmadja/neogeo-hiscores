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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.anzymus.neogeo.hiscores.comparator.ScoreSortedByValueDescComparator;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;

@Stateless
public class HallOfFameService {

    @EJB
    ScoreService scoreService;
    @EJB
    GameService gameService;
    private static Comparator<Score> sortScoreByValueDesc = new ScoreSortedByValueDescComparator();
    private static final int[] POINTS = new int[] { 10, 8, 6, 5, 4, 3, 2, 1 };

    public List<Player> getPlayersOrderByRank(String level) {
        Map<String, Player> players = new HashMap<String, Player>();

        List<Game> games = gameService.findAll();
        for (Game game : games) {
            Scores scores = scoreService.findAllByGame(game);
            List<Score> scoresByLevel = scores.getScoresByLevel(level);
            Collections.sort(scoresByLevel, sortScoreByValueDesc);
            Score oldScore = null;
            int oldPoint = 0;
            for (int i = 0; i < scoresByLevel.size() && i < 8; i++) {
                Score score = scoresByLevel.get(i);
                int point = POINTS[i];
                if (oldScore != null && score.getValue().equals(oldScore.getValue())) {
                    point = oldPoint;
                }
                Player scorePlayer = score.getPlayer();
                String fullname = scorePlayer.getFullname();
                Player player = players.get(fullname);
                if (player == null) {
                    player = new Player(fullname);
                    players.put(fullname, player);
                }
                player.setScore(player.getPoints() + point);
                player.setContribution(player.getContribution() + 1);
                oldScore = score;
                oldPoint = point;
            }
        }

        List<Player> sortedPlayers = new ArrayList<Player>();
        sortedPlayers.addAll(players.values());
        Collections.sort(sortedPlayers, new Comparator<Player>() {

            @Override
            public int compare(Player p1, Player p2) {
                return p2.getPoints() - p1.getPoints();
            }
        });
        return sortedPlayers;
    }
}
