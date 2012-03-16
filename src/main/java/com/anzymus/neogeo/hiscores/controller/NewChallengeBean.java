package com.anzymus.neogeo.hiscores.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import com.anzymus.neogeo.hiscores.clients.AuthenticationFailed;
import com.anzymus.neogeo.hiscores.clients.NeoGeoFansClient;
import com.anzymus.neogeo.hiscores.clients.NeoGeoFansClientFactory;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.exception.ChallengeNotCreatedException;
import com.anzymus.neogeo.hiscores.service.ChallengeService;
import com.anzymus.neogeo.hiscores.service.GameService;
import com.anzymus.neogeo.hiscores.service.PlayerService;

@ManagedBean
public class NewChallengeBean {

	private Player player1;
	private Player player2;
	private Game game;

	@ManagedProperty(value = "#{param.player1}")
	private long player1Id;

	@ManagedProperty(value = "#{param.player2}")
	private long player2Id;

	@ManagedProperty(value = "#{param.game}")
	private long gameId;

	private String title;
	private String description;
	private String password;

	@EJB
	GameService gameService;

	@EJB
	PlayerService playerService;

	@EJB
	ChallengeService challengeService;

	private FacesContext facesContext = FacesContext.getCurrentInstance();

	private NeoGeoFansClientFactory neoGeoFansClientFactory = new NeoGeoFansClientFactory();

	@PostConstruct
	public void init() {
		Map<String, String> parameters = facesContext.getExternalContext().getRequestParameterMap();
		if (parameters.get("form:player1") != null) {
			player1Id = Long.valueOf(parameters.get("form:player1"));
			player2Id = Long.valueOf(parameters.get("form:player2"));
			gameId = Long.valueOf(parameters.get("form:game"));
		}
		player1 = playerService.findById(player1Id);
		player2 = playerService.findById(player2Id);
		game = gameService.findById(gameId);
	}

	public String add() {
		NeoGeoFansClient ngfClient = neoGeoFansClientFactory.create();
		try {
			boolean isAuthenticated = ngfClient.authenticate(player1.getFullname(), password);
			if (isAuthenticated) {
				challengeService.createChallenge(player1, player2, game, title, description);
				return "home";
			} else {
				facesContext.addMessage(null, new FacesMessage("Your NGF account is invalid"));
				return "challenge/create_new_challenge";
			}
		} catch (ChallengeNotCreatedException e) {
			facesContext.addMessage(null, new FacesMessage(e.getMessage()));
			return "challenge/create_new_challenge";
		} catch (AuthenticationFailed e) {
			facesContext.addMessage(null, new FacesMessage(e.getMessage()));
			return "challenge/create_new_challenge";
		}
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public long getPlayer1Id() {
		return player1Id;
	}

	public void setPlayer1Id(long player1Id) {
		this.player1Id = player1Id;
	}

	public long getPlayer2Id() {
		return player2Id;
	}

	public void setPlayer2Id(long player2Id) {
		this.player2Id = player2Id;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

}
