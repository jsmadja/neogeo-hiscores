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

package com.anzymus.neogeo.hiscores.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.success.FirstScoreTitleStrategy;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

public class TitleUnlockingServiceITest extends AbstractTest {

    @Test
    public void should_unlock_title() {
        Player player = createPlayer();
        Game game = createGame();
        createScore(player, game);
        Title title = new Title();
        title.setClassname(FirstScoreTitleStrategy.class.getName());
        title.setDescription("add one score");
        title.setLabel("newcomer");
        title = titleService.store(title);

        titleUnlockingService.strategiesByTitle = new HashMap<Title, TitleUnlockingStrategy>();
        titleUnlockingService.strategiesByTitle.put(title, new FirstScoreTitleStrategy());

        int initialUnlockedTitles = player.getUnlockedTitles().size();

        titleUnlockingService.searchUnlockedTitlesFor(player);

        player = playerService.findByFullname(player.getFullname());
        assertTrue(initialUnlockedTitles < player.getUnlockedTitles().size());
    }

    @Ignore
    @Test
    public void should_find_best_title_unlocker() {
        createPlayer();

        Player player = createPlayer();
        Game game = createGame();
        createScore(player, game);

        titleUnlockingService.searchUnlockedTitlesFor(player);
        List<Player> bestTitleUnlockers = titleUnlockingService.findPlayersOrderByNumUnlockedTitles();

        List<Player> players = playerService.findAll();

        System.err.println(players.size() + " players");
        players.removeAll(bestTitleUnlockers);
        assertFalse(players.isEmpty());

        for (Player bestPlayer : bestTitleUnlockers) {
            int numUnlockedTitles = bestPlayer.getUnlockedTitles().size();
            for (Player p : players) {
                assertTrue(numUnlockedTitles >= p.getUnlockedTitles().size());
            }
        }
    }

}
