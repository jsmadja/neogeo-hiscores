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
