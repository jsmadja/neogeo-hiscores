package com.anzymus.neogeo.hiscores.common;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

public class ImageShackTest {

	@Test
	public void should_extract_direct_link_from_page() throws Exception {
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("imageshack/page.html");
		String directLink = new ImageShack().extractDirectLink(fis);
		assertEquals("http://img846.imageshack.us/img846/8463/wipcolor.png", directLink);
	}

	@Test(expected = DirectLinkNotFoundException.class)
	public void should_not_extract_direct_link_from_page() throws Exception {
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream("ngf/login-page.html");
		new ImageShack().extractDirectLink(fis);
	}

}
