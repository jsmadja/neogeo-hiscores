package com.anzymus.neogeo.hiscores.domain;

import static com.google.common.base.Objects.equal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

@Entity
@NamedQueries({ @NamedQuery(name = "challenge_findAllActiveChallenges", query = "SELECT c FROM Challenge c WHERE c.finishDate > :finishDate ORDER BY c.finishDate ASC") })
public class Challenge {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private Player player1;

	@ManyToOne(optional = false)
	private Player player2;

	@ManyToOne(optional = false)
	private Game game;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(name = "FINISH_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
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

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Challenge)) {
			return false;
		}
		Challenge c = (Challenge) o;
		return equal(player1, c.player1) && //
				equal(player2, c.player2) && //
				equal(game, c.game) && //
				equal(title, c.title) && //
				equal(description, c.description) && //
				equal(finishDate, c.finishDate);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(title, description);
	}
}
