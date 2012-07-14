package com.anzymus.neogeo.hiscores.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

public class DatesTest {

	@Test
	public void should_not_be_null() {
		Date lastDayOfMonth = Dates.findLastDayOfMonth();
		assertNotNull(lastDayOfMonth);
	}

}
