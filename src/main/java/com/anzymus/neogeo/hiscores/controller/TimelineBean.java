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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.service.ScoreService;

@ManagedBean
@SessionScoped
public class TimelineBean {

    @EJB
    ScoreService scoreService;

    public List<TimelineItem> getItems() {
        List<Score> scores = scoreService.findLastScoresOrderByDateDesc();
        List<TimelineItem> items = new ArrayList<TimelineItem>();
        for (Score score : scores) {
            TimelineItem item = new TimelineItem(score);
            if (score.getPictureUrl() == null || score.getPictureUrl().length()==0) {
                item.setPictureUrl("myimages/nopic.jpg");
            } else {
                item.setPictureUrl(score.getPictureUrl());
            }
            items.add(item);
        }
        return items;
    }

}