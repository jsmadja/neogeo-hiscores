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

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Level;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class AdministrationWebService {

    @EJB GameService gameService;
    
    @EJB ScoreService scoreService;
    
    @WebMethod
    public void initialize() {
        Game gameFatalFury = new Game("Fatal Fury");
        gameFatalFury.setRules("Objectif : Faire le maximum de points avec 1 credit.");
        
        Game gameSamuraiShodown = new Game("Samurai Shodown");

        gameService.add(gameFatalFury);
        gameService.add(gameSamuraiShodown);
        
        Player player = new Player("Anzymus", "ANZ");
        Score score1 = new Score("100", player, new Level("MVS"), gameFatalFury, "http://img860.imageshack.us/img860/9089/img1207jp.jpg");
        Score score2 = new Score("1mn32", player, new Level("Easy"), gameSamuraiShodown, "http://img860.imageshack.us/img860/9089/img1207jp.jpg");
        Score score3 = new Score("150", player, new Level("Normal"), gameFatalFury, "http://img860.imageshack.us/img860/9089/img1207jp.jpg");

        scoreService.add(score1);
        scoreService.add(score2);
        scoreService.add(score3);
    }
}
