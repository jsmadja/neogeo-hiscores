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

import com.google.common.base.Objects;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "GAME", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({ //
        @NamedQuery(name = "game_findByName", query = "SELECT g FROM Game g WHERE g.name = :name"), //
        @NamedQuery(name = "game_findGamesByGenre", query = "SELECT g FROM Game g WHERE g.genre = :genre") //
})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Comparable<Game>, Serializable {

    private static final long serialVersionUID = -8252960983109413218L;
    public static final Game EMPTY = new Game("");

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long postId;

    @Column(name = "GENRE", nullable = false)
    private String genre = "Unknown";

    @Column(nullable = true, name = "CUSTOM_STAGE_VALUES")
    private String customStageValues;

    @Transient
    private int contribution;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean improvable;

    @OneToMany(mappedBy = "game")
    private List<Score> scores = new ArrayList<Score>();

    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCustomStageValues() {
        return customStageValues;
    }

    public void setCustomStageValues(String customStageValues) {
        this.customStageValues = customStageValues;
    }

    public boolean isImprovable() {
        return improvable;
    }

    public void setImprovable(boolean improvable) {
        this.improvable = improvable;
    }

    @Override
    public int compareTo(Game game) {
        return name.compareTo(game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game) {
            Game g = (Game) obj;
            return name.equals(g.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    // used by Games.tml
    public int getNumScorers() {
        Set<Player> players = new HashSet<Player>();
        for (Score score : scores) {
            players.add(score.getPlayer());
        }
        return players.size();
    }

    public Scores getScores() {
        Scores scores = new Scores();
        for (Score score : this.scores) {
            scores.add(score);
        }
        return scores;
    }

    public Scores getScores(String level) {
        Scores scores = new Scores();
        for (Score score : this.scores) {
            if (score.hasLevel(level)) {
                scores.add(score);
            }
        }
        return scores;
    }

    public boolean hasName(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
