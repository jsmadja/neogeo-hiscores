package com.anzymus.neogeo.hiscores.common;

public class IntegerToRank {

	public static String getOrdinalFor(int value) {
		int hundredRemainder = value % 100;
		int tenRemainder = value % 10;
		if (hundredRemainder - tenRemainder == 10) {
			return value + "th";
		}
		switch (tenRemainder) {
		case 1:
			return value + "st";
		case 2:
			return value + "nd";
		case 3:
			return value + "rd";
		default:
			return value + "th";
		}
	}

}
