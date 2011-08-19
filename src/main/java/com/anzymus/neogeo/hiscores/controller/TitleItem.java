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

package com.anzymus.neogeo.hiscores.controller;

import com.anzymus.neogeo.hiscores.domain.Title;

public class TitleItem implements Comparable<TitleItem> {

    private Title title;
    private boolean unlocked;

    public TitleItem(Title title, boolean unlocked) {
        this.title = title;
        this.unlocked = unlocked;
    }

    public Title getTitle() {
        return title;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    @Override
    public int compareTo(TitleItem titleItem) {
        return title.compareTo(titleItem.title);
    }

}
