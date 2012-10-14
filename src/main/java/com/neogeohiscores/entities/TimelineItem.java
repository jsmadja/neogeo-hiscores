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

import com.neogeohiscores.common.ScoreConverter;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.Date;

public class TimelineItem {

    private Score score;
    private String pictureUrl;
    private String rank;

    public TimelineItem(Score score) {
        this.score = score;
    }

    public String getPlayerName() {
        return score.getPlayerName();
    }

    public Player getPlayer() {
        return score.getPlayer();
    }

    public String getComment() {
        return score.getMessage();
    }

    public String getDate() {
        Date date = score.getCreationDate();
        return new PrettyTime().format(date);
    }

    public Game getGame() {
        return score.getGame();
    }

    public String getLevel() {
        return score.getLevel();
    }

    public String getScore() {
        return new ScoreConverter().getAsString(score.getValue());
    }

    public Score getScoreValue() {
        return score;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Date getCreationDate() {
        return score.getCreationDate();
    }

}
