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

package com.neogeohiscores.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.google.common.base.Objects;
import com.neogeohiscores.common.ScoreConverter;

@Entity
@Table(name = "SCORE")
@NamedQueries({ //
@NamedQuery(name = "score_findAllByGame", query = "SELECT s FROM Score s WHERE s.game = :game"), //
        @NamedQuery(name = "score_findAllByGameAndLevel", query = "SELECT s FROM Score s WHERE s.game = :game AND s.level = :level"), //
        @NamedQuery(name = "score_findAllByGameThisMonth", query = "SELECT s FROM Score s WHERE s.game = :game AND s.creationDate >= :beginDate AND s.creationDate <= :endDate"), //
        @NamedQuery(name = "score_findAllOneCreditScoresByGame", query = "SELECT s FROM Score s WHERE s.game = :game AND s.allClear = true"), //
        @NamedQuery(name = "score_findAllByPlayer", query = "SELECT s FROM Score s WHERE s.player = :player"), //
        @NamedQuery(name = "score_findAll", query = "SELECT s FROM Score s"), //
        @NamedQuery(name = "score_findAllOrderByDateDesc", query = "SELECT s FROM Score s ORDER BY s.creationDate DESC"), //
        @NamedQuery(name = "score_getNumScoredGamesByGenres", query = "SELECT COUNT(DISTINCT s.game) FROM Score s WHERE s.player = :player AND s.game.genre IN :genres") //
})
public class Score implements Serializable {

    private static final long serialVersionUID = -5889253204083818192L;

    public static final Score EMPTY = new Score("", Player.EMPTY, "", Game.EMPTY, "");

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(optional = false)
    private Game game;

    @ManyToOne(optional = false)
    private Player player;

    @Column(name = "LEVEL_LABEL", nullable = false)
    private String level;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "SCORE_VALUE", nullable = false)
    private String value;

    @Column(name = "PICTURE_URL")
    private String pictureUrl;

    private String message;

    private String stage;

    @Column(name="RANK", nullable = true)
    private Integer rank;

    @Column(name="NGH_POINTS", nullable = true)
    private Integer nghPoints;

    @Column(name="GAME_OF_THE_DAY", nullable = true)
    private Boolean gameOfTheDay;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(columnDefinition = "TINYINT", name = "ALL_CLEAR")
    private boolean allClear;

    public Score() {
    }

    public Score(String value, Player player, String level, Game game, String pictureUrl) {
        value = adaptScore(value);
        this.value = value;
        this.player = player;
        this.level = level;
        this.game = game;
        this.pictureUrl = pictureUrl;
        this.creationDate = new Date();
    }

    private String adaptScore(String value) {
        if (value != null) {
            value = value.replaceAll(" ", "");
            value = value.replaceAll("\\.", "");
        }
        return value;
    }

    public Score(String value) {
        value = adaptScore(value);
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public String getValue() {
        return value;
    }

    public String getLevel() {
        return level;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setValue(String value) {
        value = adaptScore(value);
        this.value = value;
    }

    public boolean getAllClear() {
        return allClear;
    }

    public void setAllClear(boolean allClear) {
        this.allClear = allClear;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Score) {
            Score score = (Score) obj;
            return Objects.equal(value, score.value) && //
                    Objects.equal(game, score.game) && //
                    Objects.equal(player, score.player) && //
                    Objects.equal(allClear, score.allClear) && //
                    Objects.equal(level, score.level);
        }
        return false;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(game, value, player, level, pictureUrl);
    }

    public String getFormatted() {
        return new ScoreConverter().getAsString(this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    /***
     * Soccer are like : 6-13-2
     */
    private static final String SOCCER_PATTERN = "\\d+-\\d+-\\d+";

    public boolean isSoccer() {
        return value.matches(SOCCER_PATTERN);
    }

    /***
     * Chrono are like 12:55:50
     */
    private static final String CHRONO_PATTERN = "\\d+:\\d+:\\d+";

    public boolean isChrono() {
        return value.matches(CHRONO_PATTERN);
    }

    public boolean isClassicScore() {
        return !isSoccer() && !isChrono();
    }

    public boolean isImprovable() {
        return game.isImprovable() && isClassicScore();
    }

    /***
     * Soccer with goal average are like : 6-13-2-1+3 or 6-4-5-6-7
     */
    private static final Pattern SOCCER_WITH_GOAL_AVERAGE_PATTERN = Pattern.compile("\\d+-\\d+-\\d+-\\d+[-+]\\d+");

    public boolean isSoccerWithGoalAverage() {
        return SOCCER_WITH_GOAL_AVERAGE_PATTERN.matcher(value).matches();
    }

   public String getPlayerName() {
        return player.getFullname();
    }


    public void setNghPoints(int nghPoints) {
        this.nghPoints = nghPoints;
    }

    public void setGameOfTheDay(boolean gameOfTheDay) {
        this.gameOfTheDay = gameOfTheDay;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Integer getNghPoints() {
        return this.nghPoints;
    }
}
