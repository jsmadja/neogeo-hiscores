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

import java.util.Date;

import org.ocpsoft.pretty.time.PrettyTime;

import com.neogeohiscores.common.ScoreConverter;

public class TimelineItem {

    private Score score;
    private String pictureUrl;
    private UnlockedTitle unlockedTitle;
    private String avatarUrl;
    private Challenge challenge;
    private RelockedTitle relockedTitle;
    private String rank;

    public TimelineItem(Score score) {
        this.score = score;
    }

    public TimelineItem(UnlockedTitle unlockedTitle) {
        this.unlockedTitle = unlockedTitle;
    }

    public TimelineItem(RelockedTitle relockedTitle) {
        this.setRelockedTitle(relockedTitle);
    }

    public TimelineItem(Challenge challenge) {
        this.setChallenge(challenge);
    }

    public String getPlayerName() {
        if (isScoreItem()) {
            return score.getPlayerName();
        }
        if (isUnlockedTitleItem()) {
            return unlockedTitle.getPlayerName();
        }
        if (isChallengeItem()) {
            return "";
        }
        if (isRelockedTitleItem()) {
            return relockedTitle.getPlayer().getFullname();
        }
        return "";
    }

    public Player getPlayer() {
        if (isScoreItem()) {
            return score.getPlayer();
        }
        if (isUnlockedTitleItem()) {
            return unlockedTitle.getPlayer();
        }
        if (isRelockedTitleItem()) {
            return relockedTitle.getPlayer();
        }
        throw new IllegalStateException();
    }

    public String getComment() {
        if (isScoreItem()) {
            return score.getMessage();
        }
        if (isUnlockedTitleItem()) {
            return unlockedTitle.getTitle().getDescription();
        }
        if (isChallengeItem()) {
            return challenge.getDescription();
        }
        return "";
    }

    public boolean isUnlockedTitleItem() {
        return unlockedTitle != null;
    }

    public String getDate() {
        Date date = new Date();
        if (isScoreItem()) {
            date = score.getCreationDate();
        }
        if (isUnlockedTitleItem()) {
            date = unlockedTitle.getUnlockDate();
        }
        if (isChallengeItem()) {
            date = challenge.getCreationDate();
        }
        return new PrettyTime().format(date);
    }

    public boolean isChallengeItem() {
        return challenge != null;
    }

    public Game getGame() {
        if (isScoreItem()) {
            return score.getGame();
        }
        if (isChallengeItem()) {
            return challenge.getGame();
        }
        if (isRelockedTitleItem()) {
            return relockedTitle.getGame();
        }
        throw new IllegalStateException();
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

    public UnlockedTitle getUnlockedTitle() {
        return unlockedTitle;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setRelockedTitle(RelockedTitle relockedTitle) {
        this.relockedTitle = relockedTitle;
    }

    public RelockedTitle getRelockedTitle() {
        return relockedTitle;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Date getCreationDate() {
        if (isScoreItem()) {
            return score.getCreationDate();
        }
        if (isChallengeItem()) {
            return challenge.getCreationDate();
        }
        if (isUnlockedTitleItem()) {
            return unlockedTitle.getUnlockDate();
        }
        return new Date();
    }

    public boolean isScoreItem() {
        return score != null;
    }

    public String getTitleName() {
        if (isUnlockedTitleItem()) {
            return unlockedTitle.getTitle().getLabel();
        }
        if (isRelockedTitleItem()) {
            return relockedTitle.getTitle().getLabel();
        }
        return "";
    }

    public boolean isRelockedTitleItem() {
        return relockedTitle != null;
    }

    public String getRelockerName() {
        return relockedTitle.getRelockerScore().getPlayerName();
    }

    public String getChallenger1Name() {
        return challenge.getPlayer1Name();
    }

    public String getChallenger2Name() {
        return challenge.getPlayer2().getFullname();
    }

}
