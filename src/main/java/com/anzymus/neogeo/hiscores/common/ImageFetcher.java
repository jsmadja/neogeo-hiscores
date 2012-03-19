package com.anzymus.neogeo.hiscores.common;

import java.net.URL;

public class ImageFetcher {

	private ImageShack imageShack = new ImageShack();

	public String get(String url) {
		try {
			return imageShack.extractDirectLink(new URL(url).openStream());
		} catch (Exception e) {
			return url;
		}
	}

}
