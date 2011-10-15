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
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Score;

public class ScoreComparatorTest {

    @Test
    public void should_return_max_of_two_scores_as_int() {
        Score score1 = new Score("1");
        Score score2 = new Score("2");
        assertEquals(score2, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_return_max_of_two_scores_as_int_with_all_clear_value() {
        Score score1 = new Score("1");
        score1.setAllClear(true);
        Score score2 = new Score("2");
        assertEquals(score1, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_return_max_of_two_scores_as_int_inverse() {
        Score score1 = new Score("2");
        Score score2 = new Score("1");
        assertEquals(score1, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_return_max_of_two_scores_as_soccer_with_max_won() {
        Score score1 = new Score("6-13-2");
        Score score2 = new Score("7-5-4");
        assertEquals(score2, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_return_max_of_two_scores_as_soccer_with_max_won_with_all_clear_value() {
        Score score1 = new Score("6-13-2");
        score1.setAllClear(true);
        Score score2 = new Score("7-5-4");
        assertEquals(score1, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_return_max_of_two_scores_as_soccer_with_max_goal_for() {
        Score score1 = new Score("6-13-2");
        Score score2 = new Score("6-5-4");
        assertEquals(score1, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_return_max_of_two_scores_as_soccer_with_max_goal_against() {
        Score score1 = new Score("6-5-2");
        Score score2 = new Score("6-5-4");
        assertEquals(score1, ScoreComparator.max(score1, score2));
    }

    @Test
    public void should_find_gap_for_int() {
        Score score1 = new Score("123");
        Score score2 = new Score("345");
        assertEquals("222", ScoreComparator.gap(score1, score2));
    }

    @Test
    public void should_find_gap_for_chrono() {
        Score score1 = new Score("6:55:78");
        Score score2 = new Score("7:28:10");
        assertEquals("0:32:32", ScoreComparator.gap(score1, score2));
    }

    @Test
    public void should_find_gap_for_chrono_10_minutes() {
        Score score1 = new Score("6:00:00");
        Score score2 = new Score("16:00:00");
        assertEquals("10:00:00", ScoreComparator.gap(score1, score2));
    }

    @Test
    public void should_find_gap_for_chrono_same_titme() {
        Score score1 = new Score("6:00:00");
        Score score2 = new Score("6:00:00");
        assertEquals("0:00:00", ScoreComparator.gap(score1, score2));
    }

    @Test
    public void should_find_gap_for_big_length() {
        Score score1 = new Score("0:00:00");
        Score score2 = new Score("16:00:00");
        assertEquals("16:00:00", ScoreComparator.gap(score1, score2));
    }

    @Test
    public void should_find_gap_for_soccer() {
        Score score1 = new Score("6-13-2");
        Score score2 = new Score("7-5-4");
        assertEquals("1-8-2", ScoreComparator.gap(score1, score2));
    }

    @Test
    public void should_not_fail_at_max_if_throwable() {
        Score score1 = new Score("1234");
        Score maxScore = ScoreComparator.max(score1, null);

        assertEquals(score1, maxScore);
    }

    @Test
    public void should_not_fail_at_gap_if_throwable() {
        Score score1 = new Score("1234");
        String gap = ScoreComparator.gap(score1, null);

        assertEquals("", gap);
    }

}
