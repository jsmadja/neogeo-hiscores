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

package com.anzymus.neogeo.hiscores.controller;

import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Level;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import java.util.Set;
import java.util.TreeSet;

@ManagedBean
@SessionScoped
public class ScoreBean {

    @EJB ScoreService scoreService;
    @EJB GameService gameService;

    private String fullname = "login";
    private String shortname = "SNK";
    private String score = "score";
    private String password = "password";
    private String pictureUrl = "picture url";
    
    private String currentGame;
    private String currentLevel;
    
    public String add() {
        Score scoreToAdd = new Score(score, new Player(fullname, shortname.toUpperCase()), new Level(currentLevel), new Game(currentGame), pictureUrl);
        scoreService.add(scoreToAdd);
        return "home";
    }
    
    public Set<String> getGameList() {
        Set<String> gameNames = new TreeSet<String>();
        Set<Game> games = gameService.findAll();
        for (Game game:games) {
            gameNames.add(game.getName());
        }
        return gameNames;
    }
    
    public List<String> getLevelList() {
        return Arrays.asList("Easy", "Normal", "MVS", "Hard");
    }

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public GameService getGameService() {
        return gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public ScoreService getScoreService() {
        return scoreService;
    }

    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}