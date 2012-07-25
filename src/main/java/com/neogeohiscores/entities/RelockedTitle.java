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
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

import static com.google.common.base.Objects.equal;

@Entity
@Table(name = "RELOCKED_TITLE")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RelockedTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Title title;

    @Column(name = "RELOCK_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date relockDate;

    @ManyToOne
    private Score relockerScore;

    public RelockedTitle() {
        relockDate = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelockedTitle) {
            RelockedTitle u = (RelockedTitle) obj;
            return equal(player, u.player) && equal(title, u.title);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(player, title);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Score getRelockerScore() {
        return relockerScore;
    }

    public void setRelockerScore(Score relockerScore) {
        this.relockerScore = relockerScore;
    }

    public void setRelockDate(Date relockDate) {
        this.relockDate = relockDate;
    }

    public Date getRelockDate() {
        return relockDate;
    }

    public Game getGame() {
        return relockerScore.getGame();
    }

}
