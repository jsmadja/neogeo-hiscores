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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import com.google.common.base.Objects;

@Entity
@Table(name = "PLAYER", uniqueConstraints = @UniqueConstraint(columnNames = { "fullname" }))
@NamedQueries({ @NamedQuery(name = "player_findByFullname", query = "SELECT p FROM Player p WHERE p.fullname = :fullname") })
public class Player implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Transient
    private int points;

    @Transient
    private int contribution;

    public Player() {
    }

    public Player(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return fullname;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fullname);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            return Objects.equal(fullname, player.fullname);
        }
        return false;
    }

    public String getFullname() {
        return fullname;
    }

    public int getPoints() {
        return points;
    }

    public void setScore(int score) {
        this.points = score;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
