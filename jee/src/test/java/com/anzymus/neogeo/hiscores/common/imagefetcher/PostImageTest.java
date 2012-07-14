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

package com.anzymus.neogeo.hiscores.common.imagefetcher;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.common.imagefetcher.DirectLinkNotFoundException;
import com.anzymus.neogeo.hiscores.common.imagefetcher.PostImage;
import com.google.common.io.ByteStreams;

public class PostImageTest {

	@Test
	public void should_extract_direct_link_from_page() throws Exception {
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("post-image/page.html");
		String directLink = new PostImage().extractDirectLink(new String(ByteStreams.toByteArray(fis)));
		assertEquals("http://s14.postimage.org/6nd148x4x/100_3574.jpg", directLink);
	}

	@Test(expected = DirectLinkNotFoundException.class)
	public void should_not_extract_direct_link_from_page() throws Exception {
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("ngf/login-page.html");
		new PostImage().extractDirectLink(new String(ByteStreams.toByteArray(fis)));
	}

}
