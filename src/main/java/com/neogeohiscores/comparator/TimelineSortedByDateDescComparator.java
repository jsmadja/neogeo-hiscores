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

package com.neogeohiscores.comparator;

import com.neogeohiscores.entities.TimelineItem;

import java.util.Comparator;
import java.util.Date;

public class TimelineSortedByDateDescComparator implements Comparator<TimelineItem> {

    @Override
    public int compare(TimelineItem t1, TimelineItem t2) {
        Date date1 = getDate(t1);
        Date date2 = getDate(t2);
        if (date1 != null && date2 != null) {
            return date2.compareTo(date1);
        }
        return 1;
    }

    private Date getDate(TimelineItem timelineItem) {
        if (timelineItem.isScoreItem()) {
            return timelineItem.getCreationDate();
        }
        if (timelineItem.isUnlockedTitleItem()) {
            return timelineItem.getUnlockedTitle().getUnlockDate();
        }
        return timelineItem.getRelockedTitle().getRelockDate();
    }
}
