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

import org.junit.Test;

public class SoccerScoreTest {

	@Test
	public void should_compare_two_equal_score() {
		Score score = new Score("2-3-4");
		SoccerScore score1 = new SoccerScore(score);
		SoccerScore score2 = new SoccerScore(score);
		assertEquals(0, score1.compareTo(score2));
	}

	@Test
	public void should_compare_two_different_scores() {
		SoccerScore score1 = new SoccerScore(new Score("2-3-4"));
		SoccerScore score2 = new SoccerScore(new Score("2-3-5"));
		assertEquals(1, score1.compareTo(score2));
	}

	@Test
	public void should_compare_two_different_scores_reverse() {
		SoccerScore score1 = new SoccerScore(new Score("2-3-5"));
		SoccerScore score2 = new SoccerScore(new Score("2-3-4"));
		assertEquals(-1, score1.compareTo(score2));
	}
}
