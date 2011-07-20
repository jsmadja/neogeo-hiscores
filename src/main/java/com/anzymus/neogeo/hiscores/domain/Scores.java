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

package com.anzymus.neogeo.hiscores.domain;

import java.util.Set;
import java.util.TreeSet;

public class Scores {

    Set<Score> scores = new TreeSet<Score>();

    public void add(Score score) {
        scores.add(score);
    }

    public boolean contains(Score score) {
        return scores.contains(score);
    }

    public int count() {
        return scores.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Score score : scores) {
            sb.append(score).append("\n");
        }
        return sb.toString();
    }

    public Score[] toArray() {
        return scores.toArray(new Score[scores.size()]);
    }

}
