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

package com.neogeohiscores.entities;

import com.anzymus.neogeo.hiscores.success.Step;

import java.util.ArrayList;
import java.util.List;

public class Achievement {

    private List<Step> steps = new ArrayList<Step>();

    private int progressInPercent;

    private Title title;

    public Achievement(Title title, int progressInPercent) {
        this.setTitle(title);
        this.progressInPercent = progressInPercent;
    }

    public int getProgressInPercent() {
        return progressInPercent;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Title getTitle() {
        return title;
    }
}