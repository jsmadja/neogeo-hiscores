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

package com.anzymus.neogeo.hiscores.success;

import com.neogeohiscores.entities.Title;
import com.neogeohiscores.web.services.TitleService;

public abstract class AbstractTitleStrategy implements TitleUnlockingStrategy {

    protected TitleService titleService;
    protected Title title;

    public void initialize(TitleService titleService) {
        this.titleService = titleService;
    }

    protected int percent(long up, long down) {
        return (int) (up * 100 / down);
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public void setTitle(Title title) {
        this.title = title;
    }

}
