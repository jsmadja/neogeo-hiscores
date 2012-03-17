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

import org.junit.Test;

public class ChangesServiceTest {

	ChangesService service = new ChangesService();

	@Test
	public void should_have_first_place_when_we_are_the_first_to_score() {
		service.setCurrentValues("Bob;20");
		String changes = service.getChanges("Bob");
		assertEquals("Bob is now at the 1st place!", changes);
	}

	@Test
	public void should_have_first_place_when_we_are_to_players_to_score() {
		service.setLastValues("Mike;10");
		service.setCurrentValues("Bob;20\nMike;10");
		String changes = service.getChanges("Bob");
		assertEquals("Bob is now at the 1st place!", changes);
		changes = service.getChanges("Mike");
		assertEquals("Mike is now at the 2nd place!", changes);
	}

	@Test
	public void should_not_have_changes_if_there_is_no_differences() {
		service.setLastValues("Bob;20\nMike;10");
		service.setCurrentValues("Bob;20\nMike;11");
		String changes = service.getChanges("Bob");
		assertEquals("", changes);
		changes = service.getChanges("Mike");
		assertEquals("", changes);
	}
}
