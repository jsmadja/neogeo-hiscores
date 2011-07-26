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

package com.anzymus.neogeo.hiscores.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.google.common.base.Objects;

@Entity
@Table(name = "SCORE")
@NamedQueries({
        @NamedQuery(name = "score_findAllByGame", query = "SELECT s FROM Score s WHERE s.game = :game"),
        @NamedQuery(name = "score_findAllByPlayer", query = "SELECT s FROM Score s WHERE s.player = :player"),
        @NamedQuery(name = "score_findAll", query = "SELECT s FROM Score s"),
        @NamedQuery(name = "score_findAllOrderByDateDesc", query = "SELECT s FROM Score s ORDER BY s.creationDate DESC") })
public class Score implements Serializable {

    @Id
    @GeneratedValue
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

    public Score() {
    }

    public Score(String value, Player player, String level, Game game, String pictureUrl) {
        this.value = value;
        this.player = player;
        this.level = level;
        this.game = game;
        this.pictureUrl = pictureUrl;
        this.creationDate = new Date();
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
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Score) {
            Score score = (Score) obj;
            return Objects.equal(value, score.value) && //
                    Objects.equal(game, score.game) && //
                    Objects.equal(player, score.player) && //
                    Objects.equal(level, score.level);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(game, value, player, level, pictureUrl);
    }

}
