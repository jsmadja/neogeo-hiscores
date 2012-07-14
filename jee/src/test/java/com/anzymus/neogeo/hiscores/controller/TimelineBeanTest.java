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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Timeline;
import com.anzymus.neogeo.hiscores.domain.TimelineItem;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.service.TimelineService;

public class TimelineBeanTest {

    @Mock
    TimelineService timelineService;

    TimelineBean timelineBean;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        timelineBean = new TimelineBean();
        timelineBean.timelineService = timelineService;
    }

    @Test
    public void should_build_timeline_item_list() {
        Title title = new Title();
        Player player = new Player();
        UnlockedTitle unlockedTitle = new UnlockedTitle(player, title);

        TimelineItem timelineItem = new TimelineItem(unlockedTitle);

        Timeline timeline = new Timeline();
        timeline.getItems().add(timelineItem);

        when(timelineService.createTimeline()).thenReturn(timeline);

        timelineBean.init();

        List<TimelineItem> items = timelineBean.getItems();

        assertEquals(timeline.getItems().size(), items.size());
    }

}
