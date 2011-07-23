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
import com.anzymus.neogeo.hiscores.service.ScoreService;

@WebService
public class AdministrationWebService {

    @EJB GameService gameService;
    
    @EJB ScoreService scoreService;
    
    @WebMethod
    public void initialize() {
        initializeScoreList();
    }
    
    @WebMethod
    public void initializeGameList(String list) {
        String[] games = list.split(";");
        for(String gameName:games) {
            addGame(gameName.trim());
        }
    }
    
    private void initializeScoreList() {
        addScore("Robo Army", "MVS", "Twilight_Guardian", "TGU", "86900", "http://img860.imageshack.us/img860/2171/img03592.jpg");
        addScore("Robo Army", "MVS", "just", "JUS", "76900", "http://img232.imageshack.us/img232/8680/003hob.jpg");
        addScore("Robo Army", "MVS", "juanito1979", "JLO", "39800", "http://img25.imageshack.us/img25/5374/picture121oh5.jpg");
        addScore("Robo Army", "MVS", "kurush75", "KUR", "32900", "http://img821.imageshack.us/img821/4363/roboarmy.jpg");
        addScore("Robo Army", "MVS", "aescdmvs", "AES", "31600", "http://i43.tinypic.com/28atapw.jpg");
        
        addScore("Sengoku 2 / Sengoku Denshou 2", "MVS", "NeoJin", "AAA", "115200", "http://img707.imageshack.us/img707/9459/sengoku2score.jpg");
        addScore("Sengoku 2 / Sengoku Denshou 2", "MVS", "juanito1979", "JLO", "111400", "http://img188.imageshack.us/img188/6392/p7080745.jpg");
        addScore("Sengoku 2 / Sengoku Denshou 2", "MVS", "Redemslug", "KAN", "108800", "http://img97.imageshack.us/img97/4318/sengokuii.jpg");
        addScore("Sengoku 2 / Sengoku Denshou 2", "MVS", "Mpower", "MPO", "100300", "http://valou.ludo.free.fr/NEO/Sengoku2.JPG");
        addScore("Sengoku 2 / Sengoku Denshou 2", "MVS", "aescdmvs", "AES", "77650", "http://i41.tinypic.com/335gyvk.jpg");
    }
    
    @WebMethod
    public void addScore(String gameName, String level, String fullname, String shortname, String scorePoints, String pictureUrl) {
        Game game = new Game(gameName);
        Player player = new Player(fullname, shortname);
        Score score = new Score(scorePoints, player, level, game, pictureUrl);
        scoreService.add(score);
    }
        
    @WebMethod
    public void addGame(String name) {
        Game game = new Game(name);
        gameService.add(game);
    }
    
}
