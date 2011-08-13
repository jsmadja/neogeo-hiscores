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

package com.anzymus.neogeo.hiscores.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.service.halloffame.HallOfFameService;

public class HallOfFameBeanTest {

    HallOfFameBean hallOfFameBean;

    @Mock
    HallOfFameService hallOfFameService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        hallOfFameBean = new HallOfFameBean();
        hallOfFameBean.hallOfFameService = hallOfFameService;
    }

    @Test
    public void should_order_by_average_when_points_are_equals() {
        Player player1 = new Player("player1");
        player1.setContribution(5);
        player1.setPoints(10);

        Player player2 = new Player("player2");
        player2.setContribution(1);
        player2.setPoints(10);

        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);

        when(hallOfFameService.getPlayersOrderByRank(anyString())).thenReturn(players);

        hallOfFameBean.init();

        PlayerItem playerItem = hallOfFameBean.getPlayers().get(0);
        assertEquals("player2", playerItem.getFullname());
    }

}
