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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageFetcher {

    private Logger logger = LoggerFactory.getLogger(ImageFetcher.class);

    private List<PictureHost> pictureHosts = new ArrayList<PictureHost>() {
        {
            add(new ImageShack());
            add(new PostImage());
            add(new HostingPics());
        }
    };

    public String get(String content) throws DirectLinkNotFoundException {
        for (PictureHost pictureHost : pictureHosts) {
            try {
                return pictureHost.extractDirectLink(content);
            } catch (DirectLinkNotFoundException e) {
                logger.info("Cannot find direct link in " + pictureHost.getClass().getSimpleName() + " for url: " + content);
            }
        }
        throw new DirectLinkNotFoundException("Cannot find directLink");
    }

    public void setPictureHosts(List<PictureHost> pictureHosts) {
        this.pictureHosts = pictureHosts;
    }

}
