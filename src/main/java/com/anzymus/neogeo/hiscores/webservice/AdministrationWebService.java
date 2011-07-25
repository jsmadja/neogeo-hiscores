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

package com.anzymus.neogeo.hiscores.webservice;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@WebService
public class AdministrationWebService {

    @EJB
    GameService gameService;

    @EJB
    ScoreService scoreService;

    @EJB
    PlayerService playerService;

    @WebMethod
    public void initializeGameList(String list) {
        String[] games = list.split(";");
        for (String gameName : games) {
            addGame(gameName.trim());
        }
    }

    @WebMethod
    public void initializeScoreList(String list) {
        String[] scores = list.split(",");
        for (String scoreLine : scores) {
            String[] scoreValue = scoreLine.split(";");
            for (int i = 0; i < scoreValue.length; i++) {
                scoreValue[i] = scoreValue[i].replaceAll("\"", "").trim();
            }
            String fullname = scoreValue[1];
            addScore(scoreValue[0], "MVS", fullname, scoreValue[2], scoreValue[3]);
        }
    }

    @WebMethod
    public void addScore(String gameName, String level, String fullname, String scorePoints, String pictureUrl) {
        Game game = gameService.findByName(gameName);
        Player player = playerService.findByFullname(fullname);
        if (player == null) {
            player = playerService.store(new Player(fullname));
        }
        Score score = new Score(scorePoints, player, level, game, pictureUrl);
        scoreService.store(score);
    }

    @WebMethod
    public void addGame(String name) {
        Game game = new Game(name);
        gameService.store(game);
    }

}
