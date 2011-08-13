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

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class NeoGeoFansClientTest {

    @Test
    public void should_format_score() {
        Player player = new Player();
        String value = "12345";
        Game game = new Game("game");
        String level = "MVS";
        String pictureUrl = "http://www.image.com/id/1";
        Score score = new Score(value, player, level, game, pictureUrl);
        score.setMessage("(stage 5)");

        String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
                + "[I](stage 5)[/I]\n" // 
                + "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
        assertEquals(expected, NeoGeoFansClient.toMessage(score));
    }
}
