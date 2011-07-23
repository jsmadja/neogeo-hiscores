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
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import java.util.Set;

@ManagedBean
@SessionScoped
public class ScoreBean {

    @EJB ScoreService scoreService;
    @EJB GameService gameService;

    private String fullname = "";
    private String shortname = "";
    private String score = "";
    private String password = "";
    private String pictureUrl = "";
    private String message;
    
    private int currentGame;
    private String currentLevel = "MVS";
    
    private static final int MAX_MESSAGE_LENGTH = 255;
    
    public String add() {
        Game game = gameService.findById(currentGame);
        Score scoreToAdd = new Score(score, new Player(fullname, shortname.toUpperCase()), currentLevel, game, pictureUrl);
        
        int end = message.length() > MAX_MESSAGE_LENGTH ? MAX_MESSAGE_LENGTH : message.length();
        scoreToAdd.setMessage(message.substring(0, end));
        scoreService.add(scoreToAdd);
        return "home";
    }
    public Set<Game> getGames() {
        return gameService.findAll();
    }
    
    public List<String> getLevelList() {
        return Arrays.asList("Easy", "Normal", "MVS", "Hard");
    }

    public int getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(int currentGame) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}