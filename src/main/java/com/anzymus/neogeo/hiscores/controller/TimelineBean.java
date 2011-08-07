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

import com.anzymus.neogeo.hiscores.comparator.TimelineSortedByDateDescComparator;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.service.ScoreService;
import com.anzymus.neogeo.hiscores.service.TitleUnlockingService;
import java.util.Collections;
import java.util.Comparator;
import javax.annotation.PostConstruct;

@ManagedBean
public class TimelineBean {

    @EJB
    ScoreService scoreService;
    
    @EJB
    TitleUnlockingService titleUnlockingService;
    
    private List<TimelineItem> items = new ArrayList<TimelineItem>();
    
    @PostConstruct
    public void init() {
        addLastScores(items);
        addLastUnlockedTitles(items);
        sortItemsByDateDesc(items);
    }

    public List<TimelineItem> getItems() {
        return items;
    }

    private void addLastScores(List<TimelineItem> items) {
        List<Score> scores = scoreService.findLastScoresOrderByDateDesc();
        for (Score score : scores) {
            TimelineItem item = new TimelineItem(score);
            if (score.getPictureUrl() == null || score.getPictureUrl().length()==0) {
                item.setPictureUrl("myimages/nopic.jpg");
            } else {
                item.setPictureUrl(score.getPictureUrl());
            }
            items.add(item);
        }
    }

    private void addLastUnlockedTitles(List<TimelineItem> items) {
        List<UnlockedTitle> unlockedTitles = titleUnlockingService.findLastUnlockedTitlesOrderByDateDesc();
        for (UnlockedTitle unlockTitle:unlockedTitles) {
            TimelineItem item = new TimelineItem(unlockTitle);
            item.setPictureUrl("myimages/success.png");
            items.add(item);
        }
    }

    private void sortItemsByDateDesc(List<TimelineItem> items) {
        Collections.sort(items, timelineSortedByDateDescComparator);
    }

    private static final Comparator<TimelineItem> timelineSortedByDateDescComparator = new TimelineSortedByDateDescComparator();
}
