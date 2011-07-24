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
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class ScoreBean {

    @EJB ScoreService scoreService;
    @EJB GameService gameService;

    @ManagedProperty(value = "#{param.scoreId}")
    private String id;
    
    private String fullname = "";
    private String score = "";
    private String password = "";
    private String pictureUrl = "";
    private String message;
    
    private int currentGame;
    private String currentLevel = "MVS";
    
    private static final int MAX_MESSAGE_LENGTH = 255;
    
    @PostConstruct
    public void init() {
        if (id != null) {
            Score scoreFromDb = scoreService.findById(Integer.parseInt(id));
            fullname = scoreFromDb.getPlayer().getFullname();
            score = scoreFromDb.getValue();
            pictureUrl = scoreFromDb.getPictureUrl();
            message = scoreFromDb.getMessage();
            currentLevel = scoreFromDb.getLevel();
            currentGame = scoreFromDb.getGame().getId();
        }
    }        
    
    public String add() {
        Game game = gameService.findById(currentGame);
        Score scoreToAdd = new Score(score, new Player(fullname), currentLevel, game, pictureUrl);
        
        int end = message.length() > MAX_MESSAGE_LENGTH ? MAX_MESSAGE_LENGTH : message.length();
        scoreToAdd.setMessage(message.substring(0, end));
        scoreService.add(scoreToAdd);
        return "home";
    }
    
    public String edit() {
        Game game = gameService.findById(currentGame);
        Score scoreFromDb = scoreService.findById(Integer.parseInt(id));
        scoreFromDb.setMessage(message);            
        scoreFromDb.setGame(game);
        scoreFromDb.setPictureUrl(pictureUrl);
        scoreFromDb.setLevel(currentLevel);
        scoreFromDb.setValue(score);
        gameService.update(scoreFromDb);
        return "home";
    }
    
    public Set<Game> getGames() {
        return gameService.findAll();
    }
    
    public List<String> getLevelList() {
        return Levels.list();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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