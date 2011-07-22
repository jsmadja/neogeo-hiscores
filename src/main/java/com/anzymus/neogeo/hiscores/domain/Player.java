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

import com.google.common.base.Objects;

public class Player {

    private String fullname;
    private String shortname;

    public Player(String fullname, String shortname) {
        this.fullname = fullname;
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public String getShortname() {
        return shortname;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("fullname", fullname) //
                .add("shortname", shortname) //
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fullname, shortname);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            return Objects.equal(fullname, player.fullname) && Objects.equal(shortname, player.shortname);
        }
        return false;
    }
}
