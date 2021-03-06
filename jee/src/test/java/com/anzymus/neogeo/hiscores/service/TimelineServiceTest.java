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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.anzymus.neogeo.hiscores.domain.Challenge;
import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;
import com.anzymus.neogeo.hiscores.domain.Timeline;
import com.anzymus.neogeo.hiscores.domain.TimelineItem;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.domain.UnlockedTitle;

@RunWith(MockitoJUnitRunner.class)
public class TimelineServiceTest {

	@Mock
	ScoreService scoreService;

	@Mock
	TitleUnlockingService titleUnlockingService;

	@Mock
	ChallengeService challengeService;

	@InjectMocks
	TimelineService timelineService;

	@Test
	public void should_create_empty_timeline() {
		Timeline timeline = timelineService.createTimeline();
		assertTrue(timeline.getItems().isEmpty());
	}

	@Test
	public void should_create_timeline_with_one_score() {
		Score score = new Score("123", new Player(), "MVS", new Game(), "http://");

		List<Score> scores = new ArrayList<Score>();
		scores.add(score);

		when(scoreService.findLastScoresOrderByDateDesc()).thenReturn(scores);

		Timeline timeline = timelineService.createTimeline();
		assertEquals(1, timeline.getItems().size());

		TimelineItem timelineItem = timeline.getItems().get(0);
		assertEquals(score, timelineItem.getScore());
		assertEquals(score.getPictureUrl(), timelineItem.getAvatarUrl());
		assertEquals(score.getPictureUrl(), timelineItem.getPictureUrl());
	}

	@Test
	public void should_create_timeline_with_one_score_with_no_picture() {
		Score score = new Score("123", new Player(), "MVS", new Game(), null);

		List<Score> scores = new ArrayList<Score>();
		scores.add(score);

		when(scoreService.findLastScoresOrderByDateDesc()).thenReturn(scores);

		Timeline timeline = timelineService.createTimeline();
		assertEquals(1, timeline.getItems().size());

		TimelineItem timelineItem = timeline.getItems().get(0);
		assertEquals(score, timelineItem.getScore());
		assertEquals("myimages/nopic.jpg", timelineItem.getAvatarUrl());
		assertEquals("myimages/nopic.jpg", timelineItem.getPictureUrl());
	}

	@Test
	public void should_create_timeline_with_one_score_with_avatar() {
		Player player = new Player();
		player.setAvatarId(5L);
		Score score = new Score("123", player, "MVS", new Game(), "http://");

		List<Score> scores = new ArrayList<Score>();
		scores.add(score);

		when(scoreService.findLastScoresOrderByDateDesc()).thenReturn(scores);

		Timeline timeline = timelineService.createTimeline();
		assertEquals(1, timeline.getItems().size());

		TimelineItem timelineItem = timeline.getItems().get(0);
		assertEquals(score, timelineItem.getScore());
		assertEquals("http://www.neogeofans.com/leforum/image.php?u=5", timelineItem.getAvatarUrl());
		assertEquals(score.getPictureUrl(), timelineItem.getPictureUrl());
	}

	@Test
	public void should_create_timeline_with_one_unlocked_title() {
		UnlockedTitle unlockedTitle = new UnlockedTitle(new Player(), new Title());

		List<UnlockedTitle> unlockedTitles = new ArrayList<UnlockedTitle>();
		unlockedTitles.add(unlockedTitle);

		when(titleUnlockingService.findLastUnlockedTitlesOrderByDateDesc()).thenReturn(unlockedTitles);

		Timeline timeline = timelineService.createTimeline();
		assertEquals(1, timeline.getItems().size());

		TimelineItem timelineItem = timeline.getItems().get(0);
		assertEquals(unlockedTitle, timelineItem.getUnlockedTitle());
		assertEquals("myimages/success.png", timelineItem.getPictureUrl());
	}

	@Test
	public void should_create_timeline_with_one_challenge() {
		Challenge challenge = new Challenge();
		Player player1 = new Player("player1");
		Player player2 = new Player("player2");
		Date creationDate = new Date();
		Game game = new Game("game");
		challenge.setPlayer1(player1);
		challenge.setPlayer2(player2);
		challenge.setGame(game);
		challenge.setCreationDate(creationDate);

		Collection<Challenge> challenges = new ArrayList<Challenge>();
		challenges.add(challenge);

		when(challengeService.findAllActiveChallenges()).thenReturn(challenges);

		Timeline timeline = timelineService.createTimeline();
		assertEquals(1, timeline.getItems().size());

		TimelineItem timelineItem = timeline.getItems().get(0);
		assertEquals(challenge, timelineItem.getChallenge());
		assertEquals("myimages/challenge.png", timelineItem.getPictureUrl());
	}
}
