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

package com.anzymus.neogeo.hiscores.comparator;

import com.anzymus.neogeo.hiscores.domain.TimelineItem;
import java.util.Comparator;
import java.util.Date;

public class TimelineSortedByDateDescComparator implements Comparator<TimelineItem> {

    @Override
    public int compare(TimelineItem t1, TimelineItem t2) {
        Date date1 = getDate(t1);
        Date date2 = getDate(t2);
        return date2.compareTo(date1);
    }

    private Date getDate(TimelineItem timelineItem) {
        if (timelineItem.getScore() != null) {
            return timelineItem.getScore().getCreationDate();
        }
        return timelineItem.getUnlockedTitle().getUnlockDate();
    }

}
