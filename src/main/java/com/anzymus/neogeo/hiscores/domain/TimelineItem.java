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


public class TimelineItem {
    
    private Score score;
    private String pictureUrl;
    private UnlockedTitle unlockedTitle;
    private String avatarUrl;

    public TimelineItem(Score score) {
        this.score = score;
    }

    public TimelineItem(UnlockedTitle unlockedTitle) {
        this.unlockedTitle = unlockedTitle;
    }
    
    public Score getScore() {
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
    
    
}
