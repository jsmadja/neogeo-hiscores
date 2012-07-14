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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.anzymus.neogeo.hiscores.common.imagefetcher.DirectLinkNotFoundException;
import com.anzymus.neogeo.hiscores.common.imagefetcher.HostingPics;
import com.anzymus.neogeo.hiscores.common.imagefetcher.ImageFetcher;
import com.anzymus.neogeo.hiscores.common.imagefetcher.ImageShack;
import com.anzymus.neogeo.hiscores.common.imagefetcher.PictureHost;
import com.anzymus.neogeo.hiscores.common.imagefetcher.PostImage;

@RunWith(MockitoJUnitRunner.class)
public class ImageFetcherTest {

	@InjectMocks
	ImageFetcher imageFetcher;

	@Mock
	ImageShack imageShack;

	@Mock
	PostImage postImage;

	@Mock
	HostingPics hostingPics;

	private Throwable directLinkException = new DirectLinkNotFoundException("message");

	@Before
	public void init() {
		List<PictureHost> pictureHosts = new ArrayList<PictureHost>() {
			{
				add(imageShack);
				add(postImage);
				add(hostingPics);
			}
		};
		imageFetcher.setPictureHosts(pictureHosts);
	}

	@Test
	public void should_get_image_url_from_imageshack() throws Exception {
		imageFetcher.get("image_shack_content");
		verify(imageShack).extractDirectLink("image_shack_content");
	}

	@Test
	public void should_get_image_url_from_hosting_post_image() throws DirectLinkNotFoundException {
		when(imageShack.extractDirectLink(anyString())).thenThrow(directLinkException);
		imageFetcher.get("post_image_content");
		verify(postImage).extractDirectLink("post_image_content");
	}

	@Test
	public void should_get_image_url_from_hosting_pics() throws DirectLinkNotFoundException {
		when(imageShack.extractDirectLink(anyString())).thenThrow(directLinkException);
		when(postImage.extractDirectLink(anyString())).thenThrow(directLinkException);

		imageFetcher.get("hosting_pics_content");
		verify(hostingPics).extractDirectLink("hosting_pics_content");
	}

	@Test(expected = DirectLinkNotFoundException.class)
	public void should_not_get_image_url() throws DirectLinkNotFoundException {
		when(imageShack.extractDirectLink(anyString())).thenThrow(directLinkException);
		when(postImage.extractDirectLink(anyString())).thenThrow(directLinkException);
		when(hostingPics.extractDirectLink(anyString())).thenThrow(directLinkException);

		imageFetcher.get("invalid_content");
	}

}
