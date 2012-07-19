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

import org.apache.tapestry5.ioc.annotations.Inject;

import com.neogeohiscores.entities.Timeline;
import com.neogeohiscores.web.services.rss.TimelineRss;

public class RssService {

    @Inject
    TimelineService timelineService;

    public String getTimeline() {
        Timeline timeline = timelineService.createTimeline();
        return new TimelineRss(timeline).toString();
    }

}
