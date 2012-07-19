package com.neogeohiscores.common.imagefetcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PictureHost {

    private Pattern pattern;

    public PictureHost(String regexp) {
        pattern = Pattern.compile(regexp);
    }

    public String extractDirectLink(String content) throws DirectLinkNotFoundException {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new DirectLinkNotFoundException("Cannot find direct link in this content: " + content);
    }
}
