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
        Score score1 = new Score("100", player, new Level("MVS"), gameFatalFury);
        Score score2 = new Score("1mn32", player, new Level("Easy"), gameSamuraiShodown);
        Score score3 = new Score("150", player, new Level("Normal"), gameFatalFury);

        scoreService.add(score1);
        scoreService.add(score2);
        scoreService.add(score3);
    }
}
