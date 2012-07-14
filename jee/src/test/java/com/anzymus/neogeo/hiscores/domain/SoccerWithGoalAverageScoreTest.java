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

package com.anzymus.neogeo.hiscores.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class SoccerWithGoalAverageScoreTest {

	@Test
	public void should_order_score_by_games_then_by_goal_difference() {
		List<String> scoreList = new ArrayList<String>() {
			{
				add("7-6-13-1+12");
				add("7-7-11-3+8");
				add("7-6-8-5+3");
				add("6-6-15-8+7");
				add("6-5-10-11-1");
				add("5-4-10-6+4");
				add("4-3-8-6+2");
				add("5-4-9-7+2");
				add("5-4-8-6+2");
				add("5-4-10-9+1");
			}
		};

		Set<SoccerWithGoalAverageScore> scores = new TreeSet<SoccerWithGoalAverageScore>();
		for (String score : scoreList) {
			scores.add(new SoccerWithGoalAverageScore(new Score(score)));
		}

		assertEquals(scoreList.size(), scores.size());

		Iterator<SoccerWithGoalAverageScore> iterator = scores.iterator();
		assertEquals("7-7-11-3+8", iterator.next().getScore().getValue());
		assertEquals("7-6-13-1+12", iterator.next().getScore().getValue());
		assertEquals("7-6-8-5+3", iterator.next().getScore().getValue());
		assertEquals("6-6-15-8+7", iterator.next().getScore().getValue());
		assertEquals("6-5-10-11-1", iterator.next().getScore().getValue());
		assertEquals("5-4-10-6+4", iterator.next().getScore().getValue());
		assertEquals("5-4-9-7+2", iterator.next().getScore().getValue());
		assertEquals("5-4-8-6+2", iterator.next().getScore().getValue());
		assertEquals("5-4-10-9+1", iterator.next().getScore().getValue());
		assertEquals("4-3-8-6+2", iterator.next().getScore().getValue());
	}

	@Test
	public void should_order_with_no_full_data() {
		List<String> scoreList = new ArrayList<String>() {
			{
				add("0-0-0-0+6");
				add("0-0-0-0+7");
				add("0-0-0-0+8");
				add("6-5-11-0+11");
				add("6-5-13-2+11");
				add("7-6-13-1+12");
				add("7-6-7-1+6");
				add("7-6-9-1+8");
				add("7-6-9-2-7");
			}
		};

		Set<SoccerWithGoalAverageScore> scores = new TreeSet<SoccerWithGoalAverageScore>();
		for (String score : scoreList) {
			scores.add(new SoccerWithGoalAverageScore(new Score(score)));
		}

		assertEquals(scoreList.size(), scores.size());

		Iterator<SoccerWithGoalAverageScore> iterator = scores.iterator();
		assertEquals("7-6-13-1+12", iterator.next().getScore().getValue());
	}
}
