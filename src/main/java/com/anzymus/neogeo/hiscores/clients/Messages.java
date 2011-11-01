package com.anzymus.neogeo.hiscores.clients;

import com.anzymus.neogeo.hiscores.converter.ScoreConverter;
import com.anzymus.neogeo.hiscores.domain.Score;

public class Messages {

	private static final ScoreConverter SCORE_CONVERTER = new ScoreConverter();

	public static String toMessage(Score score) {
		String scoreValue = score.getValue();
		scoreValue = SCORE_CONVERTER.getAsString(scoreValue);
		String message = score.getMessage();
		String url = score.getPictureUrl();

		String postMessage = score.getGame().getName() + " - ";
		postMessage += "[URL=\"" + url + "\"][SIZE=\"3\"]" + scoreValue
				+ "[/SIZE][/URL]\n";
		postMessage += "[I]" + message;
		if (score.getAllClear()) {
			postMessage += " - All clear!";
		}
		postMessage += "[/I]\n";
		postMessage += "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
		return postMessage;
	}

}
