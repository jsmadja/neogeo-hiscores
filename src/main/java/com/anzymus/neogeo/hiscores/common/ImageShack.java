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
