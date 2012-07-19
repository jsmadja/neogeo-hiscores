package com.neogeohiscores.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class TimeLineItem {

    @Property
    @Parameter(allowNull = false)
    private com.neogeohiscores.entities.TimelineItem item;

}
