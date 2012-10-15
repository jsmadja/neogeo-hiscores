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

package com.neogeohiscores.common.imagefetcher;

import com.google.common.io.ByteStreams;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class HostingPicsTest {

	@Test
	public void should_extract_direct_link_from_page() throws Exception {
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("hosting-pics/page.html");
		String content = new HostingPics().extractDirectLink(new String(ByteStreams.toByteArray(fis)));
		assertEquals("http://img11.hostingpics.net/pics/4914321003570.jpg", content);
	}

	@Test(expected = DirectLinkNotFoundException.class)
	public void should_not_extract_direct_link_from_page() throws Exception {
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("ngf/login-page.html");
		String content = new HostingPics().extractDirectLink(new String(ByteStreams.toByteArray(fis)));
		new HostingPics().extractDirectLink(content);
	}

}
