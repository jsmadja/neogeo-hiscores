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

package com.anzymus.neogeo.hiscores.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScoreConverterTest {

	ScoreConverter converter = new ScoreConverter();

	@Test
	public void should_add_dot_to_score() {
		String[] scores = { "1", "12", "123", "1234", "12345", "123456", "1234567", };
		String[] scoresWithDots = { "1", "12", "123", "1.234", "12.345", "123.456", "1.234.567" };
		for (int i = 0; i < scores.length; i++) {
			String result = converter.getAsString(scores[i]);
			assertEquals(scoresWithDots[i], result);
		}
	}

	@Test
	public void should_not_format_chrono_score() {
		String result = converter.getAsString("12:55:50");
		assertEquals("12:55:50", result);
	}

	@Test
	public void should_not_format_soccer_score() {
		String result = converter.getAsString("6-13-2");
		assertEquals("6-13-2", result);
	}

	@Test
	public void should_not_format_soccer_with_goal_average_score() {
		String result = converter.getAsString("7-7-11-3+8");
		assertEquals("7-7-11-3+8", result);
	}

	@Test
	public void should_not_format_soccer__with_goal_average_score_with_overriden_method() {
		String result = converter.getAsString(null, null, "7-7-11-3+8");
		assertEquals("7-7-11-3+8", result);
	}

	@Test
	public void should_not_format_soccer_score_with_overriden_method() {
		String result = converter.getAsString(null, null, "6-13-2");
		assertEquals("6-13-2", result);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void should_throw_exception() {
		converter.getAsObject(null, null, null);
	}
}
