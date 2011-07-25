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
import com.anzymus.neogeo.hiscores.domain.Score;

public class GameItem {

    private String name;
    private long id;
    private int count;
    private Map<String, LevelItem> levelItems = new HashMap<String, LevelItem>();

    public GameItem(String name, long id) {
        this.name = name;
        this.id = id;
    }

    GameItem(String name, long id, int count) {
        this(name, id);
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void addScore(String level, Score score) {
        LevelItem levelItem = levelItems.get(level);
        if (levelItem == null) {
            levelItem = new LevelItem(level);
            levelItems.put(level, levelItem);
        }
        levelItem.addScore(score);
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
