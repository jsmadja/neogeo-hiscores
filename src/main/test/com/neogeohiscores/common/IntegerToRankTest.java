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

package com.anzymus.neogeo.hiscores.common;

import static com.anzymus.neogeo.hiscores.common.IntegerToRank.getOrdinalFor;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntegerToRankTest {

	@Test
	public void should_get_valid_ranks() {
		assertEquals("1st", getOrdinalFor(1));
		assertEquals("2nd", getOrdinalFor(2));
		assertEquals("3rd", getOrdinalFor(3));
		assertEquals("4th", getOrdinalFor(4));
		assertEquals("11th", getOrdinalFor(11));
		assertEquals("21st", getOrdinalFor(21));
	}

}
