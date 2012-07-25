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
import com.neogeohiscores.comparator.ScoreSortedByValueDescComparator;
import com.neogeohiscores.web.services.halloffame.NgfPointCalculator;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "PLAYER", uniqueConstraints = @UniqueConstraint(columnNames = {"fullname"}))
@NamedQueries({ //
        @NamedQuery(name = "player_findByFullname", query = "SELECT p FROM Player p WHERE p.fullname = :fullname"), //
        @NamedQuery(name = "player_findAll", query = "SELECT p FROM Player p ORDER BY p.fullname"), //
        @NamedQuery(name = "player_getNumberOfPlayers", query = "SELECT COUNT(p) FROM Player p") //
})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable, Comparable<Player> {

    private static final long serialVersionUID = 2067603406910806588L;

    public static final Player EMPTY = new Player("");

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Transient
    private int points;

    @Transient
    private int contribution;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UnlockedTitle> unlockedTitles = new HashSet<UnlockedTitle>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<RelockedTitle> relockedTitles = new HashSet<RelockedTitle>();

    @OneToMany(mappedBy = "player")
    private List<Score> scores = new ArrayList<Score>();

    private Long avatarId = 0L;

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

    public void setPoints(int points) {
        this.points = points;
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

    public Set<UnlockedTitle> getUnlockedTitles() {
        return unlockedTitles;
    }

    public void setUnlockedTitles(Set<UnlockedTitle> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }

    public boolean hasNotUnlocked(Title title) {
        for (UnlockedTitle unlockedTitle : unlockedTitles) {
            if (unlockedTitle.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasUnlocked(Title title) {
        return !hasNotUnlocked(title);
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public void setRelockedTitles(Set<RelockedTitle> relockedTitles) {
        this.relockedTitles = relockedTitles;
    }

    public Set<RelockedTitle> getRelockedTitles() {
        return relockedTitles;
    }

    @Override
    public int compareTo(Player p) {
        return fullname.toLowerCase().compareTo(p.getFullname().toLowerCase());
    }

    public double getAverageScoreFor() {
        String level = "MVS";
        double points = 0;
        double contributions = 0;
        ScoreSortedByValueDescComparator scoreSortedByValueDescComparator = new ScoreSortedByValueDescComparator();
        NgfPointCalculator ngfPointCalculator = new NgfPointCalculator();
        for (Game game : getPlayedGames()) {
            Scores scores = game.getScores(level);
            List<Score> scoresByLevel = scores.asSortedList();
            int maxPoints = scoresByLevel.size() >= 10 ? 10 : scoresByLevel.size();
            Collections.sort(scoresByLevel, scoreSortedByValueDescComparator);
            Score oldScore = null;
            int oldPoint = 0;
            for (int i = 0; i < scoresByLevel.size() && i < 8; i++) {
                Score score = scoresByLevel.get(i);
                int point = ngfPointCalculator.getPointsByIndex(i, maxPoints);
                if (oldScore != null && score.getValue().equals(oldScore.getValue())) {
                    point = oldPoint;
                }
                if (score.getPlayer().equals(this)) {
                    points += point;
                    contributions++;
                }
                oldScore = score;
                oldPoint = point;
            }
        }
        return points / contributions;
    }

    public Set<Game> getPlayedGames() {
        Set<Game> games = new HashSet<Game>();
        for (Score score : scores) {
            games.add(score.getGame());
        }
        return games;
    }

    public boolean isCalled(String fullname) {
        return this.fullname.equalsIgnoreCase(fullname);
    }

    public int getNumAllClearsByPlayer() {
        Set<Game> games = new HashSet<Game>();
        for (Score score : scores) {
            if (score.getAllClear()) {
                games.add(score.getGame());
            }
        }
        return games.size();
    }

    public List<Score> getScores() {
        return scores;
    }

    public boolean hasScored(String game) {
        Set<Game> playedGames = getPlayedGames();
        for (Game playedGame : playedGames) {
            if(playedGame.hasName(game)) {
                return true;
            }
        }
        return false;
    }
}
