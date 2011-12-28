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
