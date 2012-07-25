package com.neogeohiscores.web.components;

import com.neogeohiscores.entities.TimelineItem;
import com.neogeohiscores.web.services.TimelineService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class TimeLine {

    @Inject
    private TimelineService timelineService;

    @Property
    private TimelineItem item;

    public List<TimelineItem> getItems() {
        return timelineService.createTimeline().subList(0, 10);
    }

}
