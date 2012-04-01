package com.anzymus.neogeo.hiscores.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;

public class Dates {

	public static Date findLastDayOfMonth() {
		int lastDay = 31;
		DateTime lastDayOfMonth = new DateTime();
		if (lastDayOfMonth.getMonthOfYear() == 2) {
			lastDay = 29;
		}
		try {
			lastDayOfMonth.withDayOfMonth(lastDay);
		} catch (IllegalFieldValueException e) {
			lastDay = 30;
		}
		return lastDayOfMonth.withDayOfMonth(lastDay).toDate();
	}

}
