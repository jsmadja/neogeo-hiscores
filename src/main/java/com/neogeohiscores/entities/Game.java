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

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GAME", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME" }))
@NamedQueries({ //
@NamedQuery(name = "game_findAll", query = "SELECT g FROM Game g ORDER BY g.name ASC"), //
        @NamedQuery(name = "game_findByName", query = "SELECT g FROM Game g WHERE g.name = :name") //
})
public class Game implements Comparable<Game>, Serializable {

    public static final String findAllGamesPlayedBy = "SELECT * FROM GAME WHERE id IN (SELECT DISTINCT game_id FROM SCORE WHERE player_id = ?) ORDER BY name";
    public static final String findAllScoreCountForEachGames = "SELECT g.id, g.name, COUNT(s.id), g.genre FROM SCORE s, GAME g WHERE s.game_id = g.id GROUP BY g.id ORDER BY g.name";

    private static final long serialVersionUID = -8252960983109413218L;
    public static final Game EMPTY = new Game("");

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long postId;

    @Column(nullable = true, name = "CUSTOM_STAGE_VALUES")
    private String customStageValues;

    @Transient
    private int contribution;

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

    public String getCustomStageValues() {
        return customStageValues;
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

}
