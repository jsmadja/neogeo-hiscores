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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.neogeohiscores.comparator.TimelineSortedByDateDescComparator;
import com.neogeohiscores.entities.Challenge;
import com.neogeohiscores.entities.RelockedTitle;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Timeline;
import com.neogeohiscores.entities.TimelineItem;
import com.neogeohiscores.entities.UnlockedTitle;

public class TimelineService {

    @Inject
    ScoreService scoreService;

    @Inject
    TitleUnlockingService titleUnlockingService;

    @Inject
    ChallengeService challengeService;

    public Timeline createTimeline() {
        Timeline timeline = new Timeline();
        addLastScores(timeline);
        addLastUnlockedTitles(timeline);
        addLastRelockedTitles(timeline);
        addLastChallenges(timeline);
        sortItemsByDateDesc(timeline);
        return timeline;
    }

    private void addLastChallenges(Timeline timeline) {
        Collection<Challenge> challenges = challengeService.findAllActiveChallenges();
        for (Challenge challenge : challenges) {
            TimelineItem item = new TimelineItem(challenge);
            item.setPictureUrl("myimages/challenge.png");
            timeline.getItems().add(item);
        }
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
            item.setRank(scoreService.getRankOf(score));
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

    private void addLastRelockedTitles(Timeline timeline) {
        List<RelockedTitle> relockedTitles = titleUnlockingService.findLastRelockedTitlesOrderByDateDesc();
        for (RelockedTitle relockTitle : relockedTitles) {
            TimelineItem item = new TimelineItem(relockTitle);
            item.setPictureUrl("myimages/relock_title.png");
            timeline.getItems().add(item);
        }
    }

    private void sortItemsByDateDesc(Timeline timeline) {
        Collections.sort(timeline.getItems(), timelineSortedByDateDescComparator);
    }

    private static final Comparator<TimelineItem> timelineSortedByDateDescComparator = new TimelineSortedByDateDescComparator();

}
