package com.neogeohiscores.web.components;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.neogeohiscores.entities.TimelineItem;
import com.neogeohiscores.web.services.TimelineService;

public class TimeLine {

    @Inject
    private TimelineService timelineService;

    @Property
    private TimelineItem item;

    public List<TimelineItem> getItems() {
        List<TimelineItem> items = timelineService.createTimeline().getItems();
        return items.subList(0, 10);
    }

}
