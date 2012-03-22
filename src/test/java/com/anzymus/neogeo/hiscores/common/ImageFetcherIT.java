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

package com.anzymus.neogeo.hiscores.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImageFetcherIT {

	ImageFetcher imageFetcher = new ImageFetcher();

	@Test
	public void should_get_image_url_when_sending_true_image() {
		String url = imageFetcher.get("http://img846.imageshack.us/img846/8463/wipcolor.png");
		assertEquals("http://img846.imageshack.us/img846/8463/wipcolor.png", url);
	}

	@Test
	public void should_get_image_url_when_sending_image_shack_url() {
		String url = imageFetcher.get("http://imageshack.us/photo/my-images/846/wipcolor.png/");
		assertEquals("http://img846.imageshack.us/img846/8463/wipcolor.png", url);
	}

}
