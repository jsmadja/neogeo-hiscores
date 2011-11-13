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

package com.anzymus.neogeo.hiscores.clients;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import com.anzymus.neogeo.hiscores.converter.ScoreConverter;
import com.anzymus.neogeo.hiscores.domain.Score;

public class Messages {

    private static final ScoreConverter SCORE_CONVERTER = new ScoreConverter();

    private static final String MESSAGE_PATTERN = "{0} - [URL=\"{1}\"][SIZE=\"3\"]{2}[/SIZE][/URL]\n"
            + "[I]{3}{4}{5}[/I]\n" + "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";

    public static String toMessage(Score score) {
        String scoreValue = SCORE_CONVERTER.getAsString(score.getValue());
        String message = score.getMessage();
        String url = score.getPictureUrl();
        String gameName = score.getGame().getName();

        String stage = "";
        if (isNotBlank(adaptStage(score))) {
            stage = " - stage " + adaptStage(score);
        }
        String allClear = "";
        if (score.getAllClear()) {
            allClear = " - All clear!";
        }

        String postMessage = format(MESSAGE_PATTERN, gameName, url, scoreValue, message, stage, allClear);
        return postMessage;
    }

    private static String adaptStage(Score score) {
        String stage = score.getStage();
        if (stage != null) {
            stage = stage.toLowerCase();
            stage = stage.replaceAll("stage", "");
            stage = stage.trim();
        }
        return stage;
    }

}
