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

import java.util.Date;
import com.google.common.base.Objects;

public class Score {

    private Game game;
    private String value;
    private Player player;
    private Level level;
    private Date creationDate;
    private String pictureUrl;

    public Score(String value, Player player, Level level, Game game, String pictureUrl) {
        this.value = value;
        this.player = player;
        this.level = level;
        this.game = game;
        this.pictureUrl = pictureUrl;
        this.creationDate = new Date();
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

    public Level getLevel() {
        return level;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
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
        return Objects.hashCode(game, value, player, level, creationDate, pictureUrl);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .addValue(game) //
                .addValue(value) //
                .addValue(player) //
                .addValue(level) //
                .addValue(creationDate) //
                .addValue(pictureUrl) //
                .toString();
    }

}
