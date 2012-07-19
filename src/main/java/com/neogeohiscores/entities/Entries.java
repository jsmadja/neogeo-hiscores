package com.neogeohiscores.entities;

import java.util.Date;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class Entries {

    private static final String DESCRIPTION_TYPE = "text/plain";

    public static SyndEntry createEntry(String title, String link, Date date) {
        SyndEntry entry = new SyndEntryImpl();
        SyndContent description = new SyndContentImpl();
        entry.setTitle(title);
        entry.setLink(link);
        entry.setPublishedDate(date);
        description.setType(DESCRIPTION_TYPE);
        description.setValue(title);
        entry.setDescription(description);
        return entry;
    }

}
