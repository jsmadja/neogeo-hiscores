package com.anzymus.neogeo.hiscores.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.ByteStreams;

public class ImageShack {

	private Pattern p = Pattern.compile("(.*)\\[IMG\\](.*)\\[\\/IMG\\](.*)");

	public String extractDirectLink(InputStream stream) throws DirectLinkNotFoundException {
		String content = null;
		try {
			content = new String(ByteStreams.toByteArray(stream));
			Matcher matcher = p.matcher(content);
			if (matcher.find()) {
				return matcher.group(2);
			}
			throw new DirectLinkNotFoundException("Cannot find direct link in this content: " + content);
		} catch (IOException e) {
			throw new DirectLinkNotFoundException("Cannot find direct link in this content: " + content, e);
		}
	}

}
