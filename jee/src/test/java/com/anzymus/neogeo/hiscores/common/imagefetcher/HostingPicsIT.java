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
import java.net.URL;

import org.junit.Test;

import com.google.common.io.ByteStreams;

public class HostingPicsIT {

	@Test
	public void should_extract_direct_link_from_page() throws Exception {
		InputStream stream = new URL("http://hpics.li/de85219").openStream();
		byte[] bytes = ByteStreams.toByteArray(stream);
		String pageContent = new String(bytes);
		String content = new HostingPics().extractDirectLink(pageContent);
		assertEquals("http://img15.hostingpics.net/pics/364931P4130051.jpg", content);
	}

}
