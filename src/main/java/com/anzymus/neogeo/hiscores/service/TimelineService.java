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

package com.anzymus.neogeo.hiscores.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.anzymus.neogeo.hiscores.comparator.TimelineSortedByDateDescComparator;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Timeline;
import com.anzymus.neogeo.hiscores.domain.TimelineItem;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;

@Stateless
public class TimelineService {

    @EJB
    ScoreService scoreService;

    @EJB
    TitleUnlockingService titleUnlockingService;

    public Timeline createTimeline() {
        Timeline timeline = new Timeline();
        addLastScores(timeline);
        addLastUnlockedTitles(timeline);
        sortItemsByDateDesc(timeline);
        return timeline;
    }

    private void addLastScores(Timeline timeline) {
        List<Score> scores = scoreService.findLastScoresOrderByDateDesc();
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

            timeline.getItems().add(item);
        }
    }

    private void addLastUnlockedTitles(Timeline timeline) {
        List<UnlockedTitle> unlockedTitles = titleUnlockingService.findLastUnlockedTitlesOrderByDateDesc();
        for (UnlockedTitle unlockTitle : unlockedTitles) {
            TimelineItem item = new TimelineItem(unlockTitle);
            item.setPictureUrl("myimages/success.png");
            timeline.getItems().add(item);
        }
    }

    private void sortItemsByDateDesc(Timeline timeline) {
        Collections.sort(timeline.getItems(), timelineSortedByDateDescComparator);
    }

    private static final Comparator<TimelineItem> timelineSortedByDateDescComparator = new TimelineSortedByDateDescComparator();

}
