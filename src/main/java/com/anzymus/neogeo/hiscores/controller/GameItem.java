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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.anzymus.neogeo.hiscores.domain.Level;

public class GameItem {

    private String name;
    private Map<Level, LevelItem> levelItems = new HashMap<Level, LevelItem>();

    public GameItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addScore(Level level, String value) {
        LevelItem levelItem = levelItems.get(level);
        if (levelItem == null) {
            levelItem = new LevelItem(level.getLabel());
            levelItems.put(level, levelItem);
        }
        levelItem.addScore(value);
    }

    public List<LevelItem> getLevels() {
        List<LevelItem> levels = new ArrayList<LevelItem>();
        levels.addAll(levelItems.values());
        Collections.sort(levels, sortByLabelComparator);
        return levels;
    }
    private static Comparator<LevelItem> sortByLabelComparator = new Comparator<LevelItem>() {
        @Override
        public int compare(LevelItem item1, LevelItem item2) {
            return item1.getLabel().compareTo(item2.getLabel());
        }
    };
}
