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

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.RelockedTitle;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Title;

public class TitleRelockingServiceIT extends AbstractTest {

	@Test
	public void should_store_relocked_title() {
		Game game = createGame();
		Player relockerPlayer = createPlayer();
		Score relockerScore = createScore(relockerPlayer, game);
		Title title = createTitle();

		RelockedTitle relockedTitle = new RelockedTitle();
		relockedTitle.setPlayer(createPlayer());
		relockedTitle.setRelockerScore(relockerScore);
		relockedTitle.setTitle(title);

		relockedTitle = titleRelockingService.store(relockedTitle);

		assertNotNull(relockedTitle.getId());
		assertNotNull(relockedTitle.getRelockDate());
	}

	@Test
	public void should_relock_lost_title() {
		createTitle();
		Game game = createGame();
		Player scorePlayer = createPlayer();
		Score score = createScore(scorePlayer, game);

		titleUnlockingService.searchUnlockedTitlesFor(scorePlayer);
		assertEquals(1, scorePlayer.getUnlockedTitles().size());

		scoreService.delete(score.getId());

		Player relockerPlayer = createPlayer();
		Score relockerScore = createScore(relockerPlayer, game);
		titleRelockingService.relockTitles(relockerPlayer, relockerScore);

		scorePlayer = playerService.findById(scorePlayer.getId());
		assertEquals(1, scorePlayer.getRelockedTitles().size());
		scorePlayer.getUnlockedTitles().toString();
		assertEquals(0, scorePlayer.getUnlockedTitles().size());
	}

}
