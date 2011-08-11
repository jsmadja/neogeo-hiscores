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

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.Comparator;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.TimelineItem;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;

public class TimelineSortedByDateDescComparatorTest {

    @Test
    public void should_compare_two_timeline_items_of_type_score() {
        Comparator<TimelineItem> comparator = new TimelineSortedByDateDescComparator();

        Score score1 = new Score();
        Calendar date1 = Calendar.getInstance();
        date1.set(Calendar.YEAR, 5);
        score1.setCreationDate(date1.getTime());

        Score score2 = new Score();
        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.YEAR, 10);
        score2.setCreationDate(date2.getTime());

        TimelineItem timelineItem1 = new TimelineItem(score1);
        TimelineItem timelineItem2 = new TimelineItem(score2);

        int comparison = comparator.compare(timelineItem1, timelineItem2);

        assertEquals(1, comparison);
    }

    @Test
    public void should_compare_two_timeline_items_of_type_title() {
        Comparator<TimelineItem> comparator = new TimelineSortedByDateDescComparator();

        Calendar date1 = Calendar.getInstance();
        date1.set(Calendar.YEAR, 5);
        UnlockedTitle unlockTitle1 = new UnlockedTitle();
        unlockTitle1.setUnlockDate(date1.getTime());

        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.YEAR, 10);
        UnlockedTitle unlockTitle2 = new UnlockedTitle();
        unlockTitle2.setUnlockDate(date2.getTime());

        TimelineItem timelineItem1 = new TimelineItem(unlockTitle1);
        TimelineItem timelineItem2 = new TimelineItem(unlockTitle2);

        int comparison = comparator.compare(timelineItem1, timelineItem2);

        assertEquals(1, comparison);
    }

    @Test
    public void should_compare_two_timeline_items_of_type_score_and_title() {
        Comparator<TimelineItem> comparator = new TimelineSortedByDateDescComparator();

        Score score1 = new Score();
        Calendar date1 = Calendar.getInstance();
        date1.set(Calendar.YEAR, 5);
        score1.setCreationDate(date1.getTime());

        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.YEAR, 10);
        UnlockedTitle unlockTitle2 = new UnlockedTitle();
        unlockTitle2.setUnlockDate(date2.getTime());

        TimelineItem timelineItem1 = new TimelineItem(score1);
        TimelineItem timelineItem2 = new TimelineItem(unlockTitle2);

        int comparison = comparator.compare(timelineItem1, timelineItem2);

        assertEquals(1, comparison);
    }

}
