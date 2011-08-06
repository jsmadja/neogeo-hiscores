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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.success.DummyStrategy;

public class PlayerServiceITest extends AbstractTest {

    @Test
    public void should_store_player() {
        Player player = new Player("fullname");
        player = playerService.store(player);
        assertNotNull(player.getId());
    }

    @Test
    public void should_find_player() {
        Player player = new Player("find_player");
        player = playerService.store(player);

        Player foundPlayer = playerService.findByFullname(player.getFullname());
        assertNotNull(foundPlayer);
    }

    @Test
    public void should_add_new_title() {
        Player player = new Player("fullname_should_add_new_title");
        player = playerService.store(player);

        Title title = createTitle();

        playerService.unlockTitle(player, title);

        Player storedPlayer = playerService.findByFullname(player.getFullname());
        assertEquals(1, storedPlayer.getUnlockedTitles().size());
    }

    private Title createTitle() {
        Title title = new Title();
        title.setClassname(DummyStrategy.class.getName());
        title.setLabel("dummy strategy");
        title.setDescription("is always unlocked");
        title.setId(1L);
        return title;
    }
}
