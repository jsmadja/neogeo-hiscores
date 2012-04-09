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
package com.anzymus.neogeo.hiscores.service.rss;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Challenge;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.RelockedTitle;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Timeline;
import com.anzymus.neogeo.hiscores.domain.TimelineItem;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;
import com.google.common.io.ByteStreams;

public class TimelineRssTest {

	@Test
	public void should_build_empty_timeline_rss() throws Exception {
		Timeline timeline = new Timeline();
		TimelineRss timelineRss = new TimelineRss(timeline);

		String expectedRss = getFileContent("empty-timeline.rss");
		String rssTimelineAsString = clean(timelineRss.toString());

		assertEquals(expectedRss, rssTimelineAsString);
	}

	@Test
	public void should_build_one_score_timeline_rss() throws Exception {
		Player player = new Player("terry");
		Game game = new Game("Fatal Fury");
		Score score = new Score("123456", player, "MVS", game, "http://");
		Date creationDate = createDate();
		score.setCreationDate(creationDate);

		Timeline timeline = new Timeline();
		TimelineItem timelineItem = new TimelineItem(score);
		timeline.getItems().add(timelineItem);

		TimelineRss timelineRss = new TimelineRss(timeline);

		String expectedRss = getFileContent("one-score-timeline.rss");

		assertEquals(expectedRss, clean(timelineRss.toString()));
	}

	@Test
	public void should_build_one_unlocked_title_timeline_rss() throws Exception {
		Player player = new Player("terry");

		Date unlockDate = createDate();

		Title title = new Title();
		title.setLabel("World Hero");

		UnlockedTitle unlockedTitle = new UnlockedTitle(player, title);
		unlockedTitle.setPlayer(player);
		unlockedTitle.setTitle(title);
		unlockedTitle.setUnlockDate(unlockDate);

		Timeline timeline = new Timeline();
		TimelineItem timelineItem = new TimelineItem(unlockedTitle);
		timeline.getItems().add(timelineItem);

		TimelineRss timelineRss = new TimelineRss(timeline);

		String expectedRss = getFileContent("one-unlocked-title-timeline.rss");

		assertEquals(expectedRss, clean(timelineRss.toString()));
	}

	@Test
	public void should_build_one_relocked_title_timeline_rss() throws Exception {
		Player player = new Player("terry");

		Date relockDate = createDate();
		Game game = new Game("Fatal Fury");

		Title title = new Title();
		title.setLabel("World Hero");

		Score relockerScore = new Score("1", new Player("Andy"), "MVS", game, "http://");

		RelockedTitle relockedTitle = new RelockedTitle();
		relockedTitle.setPlayer(player);
		relockedTitle.setRelockDate(relockDate);
		relockedTitle.setRelockerScore(relockerScore);
		relockedTitle.setTitle(title);

		Timeline timeline = new Timeline();
		TimelineItem timelineItem = new TimelineItem(relockedTitle);
		timeline.getItems().add(timelineItem);

		TimelineRss timelineRss = new TimelineRss(timeline);

		String expectedRss = getFileContent("one-relocked-title-timeline.rss");

		assertEquals(expectedRss, clean(timelineRss.toString()));
	}

	@Test
	public void should_build_one_challenge_timeline_rss() throws Exception {
		Player player1 = new Player("terry");
		Player player2 = new Player("Andy");
		Date creationDate = createDate();
		Date finishDate = createDate();
		Game game = new Game("Fatal Fury");

		Timeline timeline = new Timeline();
		Challenge challenge = new Challenge();
		challenge.setCreationDate(creationDate);
		challenge.setDescription("description");
		challenge.setFinishDate(finishDate);
		challenge.setGame(game);
		challenge.setPlayer1(player1);
		challenge.setPlayer2(player2);
		challenge.setTitle("title");

		TimelineItem timelineItem = new TimelineItem(challenge);
		timeline.getItems().add(timelineItem);

		TimelineRss timelineRss = new TimelineRss(timeline);

		String expectedRss = getFileContent("one-challenge-timeline.rss");

		assertEquals(expectedRss, clean(timelineRss.toString()));
	}

	private Date createDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.YEAR, 2011);
		calendar.set(Calendar.DAY_OF_MONTH, 11);
		calendar.set(Calendar.HOUR_OF_DAY, 18);
		calendar.set(Calendar.MINUTE, 54);
		calendar.set(Calendar.SECOND, 8);
		Date creationDate = calendar.getTime();
		return creationDate;
	}

	private String getFileContent(String filename) throws IOException {
		Class<?> clazz = TimelineRssTest.class;
		ClassLoader classloader = clazz.getClassLoader();
		InputStream stream = classloader.getResourceAsStream("rss/timeline/" + filename);
		byte[] content = ByteStreams.toByteArray(stream);
		return clean(new String(content));
	}

	private String clean(String string) {
		string = string.replaceAll("  ", "");
		string = string.replaceAll("\t", "");
		string = string.replaceAll("\r", "");
		string = string.replaceAll("\n", "");
		return string;
	}

}
