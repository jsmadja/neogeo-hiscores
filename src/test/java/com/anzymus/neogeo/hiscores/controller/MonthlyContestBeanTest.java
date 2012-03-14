package com.anzymus.neogeo.hiscores.controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

public class MonthlyContestBeanTest {

	@Test
	public void should_get_month_in_french() {
		MonthlyContestBean contestBean = new MonthlyContestBean();
		contestBean.setLocale(Locale.FRENCH);

		String month = contestBean.getMonth();
		List<String> frenchMonths = Arrays.asList("janvier", "février", "mars", "avril", "mai", "juin", "juillet",
				"août", "septembre", "octobre", "novembre", "décembre");
		assertTrue(month + " is not a french month", frenchMonths.contains(month));
	}

}
