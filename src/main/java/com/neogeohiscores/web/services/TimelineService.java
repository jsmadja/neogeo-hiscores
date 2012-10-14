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
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Timeline;
import com.neogeohiscores.entities.TimelineItem;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import static java.util.Collections.sort;

public class TimelineService {

    @Inject
    ScoreService scoreService;

    public Timeline createTimeline() {
        Timeline timeline = new Timeline();
        addLastScores(timeline);
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
            item.setRank(scoreService.getRankOf(score));
            timeline.getItems().add(item);
        }
    }

    private void sortItemsByDateDesc(Timeline timeline) {
        sort(timeline.getItems(), new TimelineSortedByDateDescComparator());
    }

}
