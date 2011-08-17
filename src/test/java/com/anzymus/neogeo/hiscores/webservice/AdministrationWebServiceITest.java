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

package com.anzymus.neogeo.hiscores.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Set;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.anzymus.neogeo.hiscores.service.AbstractTest;
import com.anzymus.neogeo.hiscores.success.MegaShock10TitleStrategy;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

public class AdministrationWebServiceITest extends AbstractTest {

    @Test
    public void should_add_one_game() {
        String gameName = RandomStringUtils.randomAlphabetic(10);
        administrationWebService.initializeGameList(gameName);

        Game game = gameService.findByName(gameName);

        assertNotNull(game);
    }

    @Test
    public void should_initializeScoreList() {
        Game game = createGame();

        String scoreList = game.getName() + ";terry;123;http://www.google.fr;message";

        administrationWebService.initializeScoreList(scoreList);

        Scores scores = scoreService.findAllByGame(game);
        assertEquals(1, scores.count());
    }

    @Test
    public void should_delete_score() {
        Player player = createPlayer();
        Game game = createGame();
        Score score = createScore(player, game);

        administrationWebService.deleteScore(score.getId());
    }

    @Test
    public void should_unlock_new_titles() {
        Title title = new Title();
        title.setDescription("description");
        title.setLabel("label");

        TitleUnlockingStrategy titleUnlockingStrategy = new MegaShock10TitleStrategy();
        titleService.addStrategy(title, titleUnlockingStrategy);

        Player player = createPlayer();
        for (int i = 0; i < 10; i++) {
            Game game = createGame();
            createScore(player, game);
        }
        administrationWebService.unlockNewTitles();

        Player foundPlayer = playerService.findByFullname(player.getFullname());
        Set<UnlockedTitle> unlockedTitles = foundPlayer.getUnlockedTitles();
        int countUnlockedTitles = unlockedTitles.size();

        assertEquals(1, countUnlockedTitles);
    }
}
