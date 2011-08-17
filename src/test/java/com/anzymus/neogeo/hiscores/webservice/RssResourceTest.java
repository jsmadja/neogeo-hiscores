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

package com.anzymus.neogeo.hiscores.webservice;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.domain.Timeline;
import com.anzymus.neogeo.hiscores.service.RssService;
import com.anzymus.neogeo.hiscores.service.rss.TimelineRss;

public class RssResourceTest {

    @Mock
    RssService rssService;

    RssResource rssResource;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        rssResource = new RssResource();
        rssResource.rssService = rssService;
    }

    @Test
    public void should_construct_rss_timeline() {
        Timeline timeline = new Timeline();

        String rssTimeline = new TimelineRss(timeline).toString();
        when(rssService.getTimeline()).thenReturn(rssTimeline);

        rssTimeline = rssResource.getTimeline();
        assertFalse(rssTimeline.isEmpty());
    }
}
