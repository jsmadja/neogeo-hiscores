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

import com.anzymus.neogeo.hiscores.authenticator.AuthenticationFailed;
import com.anzymus.neogeo.hiscores.authenticator.NeoGeoFansAuthenticator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@ManagedBean
public class ScoreBean {

    @EJB
    ScoreService scoreService;
    @EJB
    GameService gameService;
    @EJB
    PlayerService playerService;
    @ManagedProperty(value = "#{param.scoreId}")
    private String id;
    private String fullname = "";
    private String score = "";
    private String password = "";
    private String pictureUrl = "";
    private String message;
    private long currentGame;
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
        try {
            NeoGeoFansAuthenticator authenticator = new NeoGeoFansAuthenticator();
            boolean isAuthenticated = authenticator.authenticate(fullname, password);
            if (isAuthenticated) {
                Game game = gameService.findById(currentGame);
                Player player = playerService.findByFullname(fullname);
                if (player == null) {
                    player = playerService.store(new Player(fullname));
                }
                Score scoreToAdd = new Score(score, player, currentLevel, game, pictureUrl);
                int end = message.length() > MAX_MESSAGE_LENGTH ? MAX_MESSAGE_LENGTH : message.length();
                scoreToAdd.setMessage(message.substring(0, end));
                scoreService.store(scoreToAdd);
                return "home";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your NGF is invalid"));
                return "score/create";
            }
        } catch (AuthenticationFailed ex) {
            Logger.getLogger(ScoreBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            return "score/create";
        }
    }

    public String edit() {
        try {
            NeoGeoFansAuthenticator authenticator = new NeoGeoFansAuthenticator();
            boolean isAuthenticated = authenticator.authenticate(fullname, password);
            if (isAuthenticated) {
                Game game = gameService.findById(currentGame);
                Score scoreFromDb = scoreService.findById(Integer.parseInt(id));
                scoreFromDb.setMessage(message);
                scoreFromDb.setGame(game);
                scoreFromDb.setPictureUrl(pictureUrl);
                scoreFromDb.setLevel(currentLevel);
                scoreFromDb.setValue(score);
                scoreService.store(scoreFromDb);
                return "home";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The password is not a valid NGF password"));
                return "score/edit";
            }
        } catch (AuthenticationFailed ex) {
            Logger.getLogger(ScoreBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            return "score/edit";
        }
    }

    public List<Game> getGames() {
        return gameService.findAll();
    }

    public List<String> getLevelList() {
        return Levels.list();
    }

    public long getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(long currentGame) {
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