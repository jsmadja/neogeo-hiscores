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

package com.neogeohiscores.web.services.rss;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neogeohiscores.entities.Rssable;
import com.neogeohiscores.entities.Timeline;
import com.neogeohiscores.entities.TimelineItem;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class TimelineRss {

    private static final String FEED_TYPE = "rss_2.0";

    private SyndFeed syndFeed = new SyndFeedImpl();

    public String encoding = "UTF-8";

    private Timeline timeline;

    private static final Logger LOG = LoggerFactory.getLogger(TimelineRss.class);

    public TimelineRss(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public String toString() {
        addFeedInformations();
        addEntries();
        try {
            return buildXmlContent(encoding).trim();
        } catch (IOException e) {
            LOG.warn("Can't build xml content", e);
        } catch (FeedException e) {
            LOG.warn("Can't build xml content", e);
        }
        return "";
    }

    private void addFeedInformations() {
        syndFeed.setFeedType(FEED_TYPE);
        syndFeed.setTitle("Neo Geo Hi-Scores - Timeline");
        syndFeed.setLink("http://www.neogeo-hiscores.com");
        syndFeed.setDescription("Last activity of Neo Geo Hi-Scores");
    }

    private void addEntries() {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();
        syndFeed.setEntries(entries);
        List<TimelineItem> items = timeline.getItems();
        for (TimelineItem timelineItem : items) {
            SyndEntry entry = createEntry(timelineItem);
            entries.add(entry);
        }
    }

    private SyndEntry createEntry(TimelineItem item) {
        Rssable rssable = item.getChallenge();
        if (item.getScore() != null) {
            rssable = item.getScoreValue();
        } else if (item.getUnlockedTitle() != null) {
            rssable = item.getUnlockedTitle();
        } else if (item.getRelockedTitle() != null) {
            rssable = item.getRelockedTitle();
        }
        return rssable.asEntry();
    }

    private String buildXmlContent(String encoding) throws IOException, FeedException {
        StringWriter writer = new StringWriter();
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(syndFeed, writer);
        writer.close();
        String xmlContent = writer.getBuffer().toString();
        return new String(xmlContent.getBytes(), encoding);
    }

}
