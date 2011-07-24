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

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="GAME", uniqueConstraints=@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries(
        {
            @NamedQuery(name="game_findAll", query="SELECT g FROM Game g ORDER BY g.name ASC"),
            @NamedQuery(name="game_findByName", query="SELECT g FROM Game g WHERE g.name = :name")
        }
        )
public class Game implements Comparable<Game>, Serializable {

    @Column(nullable = false)
    private String name;
    
    private String rules;
    
    @Id
    @GeneratedValue
    private Long id;

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

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRules() {
        return rules;
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

}
