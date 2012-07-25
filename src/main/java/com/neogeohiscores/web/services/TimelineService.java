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

package com.neogeohiscores.web.services;

import com.neogeohiscores.comparator.TimelineSortedByDateDescComparator;
import com.neogeohiscores.entities.RelockedTitle;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.TimelineItem;
import com.neogeohiscores.entities.UnlockedTitle;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimelineService {

    @Inject
    private ScoreBoard scoreBoard;

    @Inject
    private TitleBoard titleBoard;

    public List<TimelineItem> createTimeline() {
        List<TimelineItem> timeline = new ArrayList<TimelineItem>();
        addLastScores(timeline);
        addLastUnlockedTitles(timeline);
        addLastRelockedTitles(timeline);
        Collections.sort(timeline, new TimelineSortedByDateDescComparator());
        return timeline;
    }

    private void addLastScores(List<TimelineItem> timeline) {
        List<Score> scores = scoreBoard.findLastScoresOrderByDateDesc();
        for (Score score : scores) {
            TimelineItem item = new TimelineItem(score);
            final String pictureUrl = score.getPictureUrl();
            if (pictureUrl == null || pictureUrl.length() == 0) {
                item.setPictureUrl("myimages/nopic.jpg");
            } else {
                item.setPictureUrl(pictureUrl);
            }
            Long avatarId = score.getPlayer().getAvatarId();
            if (avatarId == null || avatarId == 0) {
                item.setAvatarUrl(item.getPictureUrl());
            } else {
                item.setAvatarUrl("http://www.neogeofans.com/leforum/image.php?u=" + avatarId);
            }
            item.setRank(score.getRank());
            timeline.add(item);
        }
    }

    private void addLastUnlockedTitles(List<TimelineItem> timeline) {
        List<UnlockedTitle> unlockedTitles = titleBoard.findLastUnlockedTitlesOrderByDateDesc();
        for (UnlockedTitle unlockTitle : unlockedTitles) {
            TimelineItem item = new TimelineItem(unlockTitle);
            item.setPictureUrl("myimages/success.png");
            timeline.add(item);
        }
    }

    private void addLastRelockedTitles(List<TimelineItem> timeline) {
        List<RelockedTitle> relockedTitles = titleBoard.findLastRelockedTitlesOrderByDateDesc();
        for (RelockedTitle relockTitle : relockedTitles) {
            TimelineItem item = new TimelineItem(relockTitle);
            item.setPictureUrl("myimages/relock_title.png");
            timeline.add(item);
        }
    }

}
