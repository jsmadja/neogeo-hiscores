package com.neogeohiscores.web.pages;

import com.neogeohiscores.common.Levels;
import com.neogeohiscores.entities.Game;
import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.web.services.GameService;
import com.neogeohiscores.web.services.PlayerService;
import com.neogeohiscores.web.services.ScoreService;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AddScore {

    public static final String DEFAULT_GAME = "3 Count Bout (3 minutes)";
    public static final Logger LOG = LoggerFactory.getLogger(AddScore.class);
    private static final String ALL_CLEAR = "ALL CLEAR";
    private static final int MAX_MESSAGE_LENGTH = 255;
    @Inject
    private ScoreService scoreService;
    @Inject
    private GameService gameService;
    @Inject
    private PlayerService playerService;
    @Property
    private Game game;
    @Property
    @Validate("required")
    private String pictureUrl;
    @Property
    @Validate("required")
    private String level;
    @Property
    @Validate("required")
    private String score;
    @Property
    private String stage;
    @Property
    private boolean allClear;
    @Property
    private String message;
    @Property
    @Validate("required")
    private String fullname;
    @InjectComponent
    private Form form;

    @InjectComponent
    private Zone imageZone;

    @Inject
    private Request request;

    @Persist
    @Property
    private Score currentScore;

    void onActivate(Score score) {
        this.currentScore = score;
    }

    @SetupRender
    void init() {
        level = "MVS";
        if (currentScore == null) {
            game = gameService.findByName(DEFAULT_GAME);
        } else {
            this.game = currentScore.getGame();
            this.score = currentScore.getValue();
            this.allClear = currentScore.getAllClear();
            this.fullname = currentScore.getPlayerName();
            this.level = currentScore.getLevel();
            this.message = currentScore.getMessage();
            this.stage = currentScore.getStage();
            this.pictureUrl = currentScore.getPictureUrl();
        }
    }

    public List<Game> getGames() {
        return gameService.findAll();
    }

    public List<String> getLevels() {
        return Levels.list();
    }

    public Object onPictureUrlChanged() {
        pictureUrl = request.getParameter("param");
        return request.isXHR() ? imageZone.getBody() : null;
    }

    @CommitAfter
    @DiscardAfter
    public Object onSuccess() {
        if (message == null) {
            message = "";
        }
        acceptScore();
        return Index.class;
    }

    private void acceptScore() {
        if (currentScore != null) {
            fullname = currentScore.getPlayerName();
        }
        Player player = playerService.findByFullname(fullname);
        if (player == null) {
            LOG.info("Enregistrement du nouvel utilisateur " + fullname);
            player = playerService.store(new Player(fullname));
        }
        LOG.info("Enregistrement du score");
        storeScore(player);
        LOG.info("Score enregistré");
    }

    private Score storeScore(Player player) {
        Score scoreToStore;
        if (currentScore == null) {
            scoreToStore = new Score(score, player, level, game, pictureUrl);
        } else {
            scoreToStore = currentScore;
            scoreToStore.setLevel(level);
            scoreToStore.setGame(game);
            scoreToStore.setPictureUrl(pictureUrl);
        }
        scoreToStore.setAllClear(allClear || ALL_CLEAR.equals(stage));
        scoreToStore.setStage(stage);
        int end = message.length() > MAX_MESSAGE_LENGTH ? MAX_MESSAGE_LENGTH : message.length();
        scoreToStore.setMessage(message.substring(0, end));
        scoreToStore = scoreService.store(scoreToStore);
        return scoreToStore;
    }

}
