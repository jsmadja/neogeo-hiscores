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

package com.anzymus.neogeo.hiscores.controller;

import java.util.Date;
import com.anzymus.neogeo.hiscores.domain.Game;

public class ScoreItem {

    private String value;
    private String rank;
    private String player;
    private String pictureUrl;
    private long id;
    private int rankNumber;
    private String level;
    private Game game;
    private String message;
    private String longMessage;
    private Date date;
    private String positiveGap;
    private String negativeGap;
    private boolean allClear;

    private int MAX_MESSAGE_LENGTH = 25;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getRankNumber() {
        return rankNumber;
    }

    public void setRankNumber(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        setLongMessage(message);
        this.message = cut(message);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public void setLongMessage(String longMessage) {
        this.longMessage = longMessage;
    }

    public String getPositiveGap() {
        return positiveGap;
    }

    public void setPositiveGap(String positiveGap) {
        this.positiveGap = positiveGap;
    }

    public String getNegativeGap() {
        return negativeGap;
    }

    public void setNegativeGap(String negativeGap) {
        this.negativeGap = negativeGap;
    }

    private String cut(String message) {
        if (message != null) {
            int length = message.length();
            if (length > MAX_MESSAGE_LENGTH) {
                message = message.substring(0, MAX_MESSAGE_LENGTH) + " ...";
            }
        }
        return message;
    }

    public void setAllClear(boolean allClear) {
        this.allClear = allClear;
    }
    
    public boolean getAllClear() {
        return allClear;
    }

}
