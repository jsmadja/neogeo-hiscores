package com.anzymus.neogeo.hiscores.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImageFetcherTest {

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
