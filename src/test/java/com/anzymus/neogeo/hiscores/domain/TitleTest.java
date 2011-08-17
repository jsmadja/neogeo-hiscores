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

import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import com.anzymus.neogeo.hiscores.success.DummyStrategy;

public class TitleTest {

    @Test
    public void should_be_valid_for_hash_set() {
        Set<Title> titles = new HashSet<Title>();

        Title title = new Title();
        title.setId(1L);
        title.setClassname(DummyStrategy.class.getName());
        title.setLabel("label");
        title.setDescription("description");

        titles.add(title);
        assertEquals(1, titles.size());

        Title title2 = new Title();
        title2.setId(1L);
        title2.setClassname(DummyStrategy.class.getName());
        title2.setLabel("label");
        title2.setDescription("description");

        titles.add(title2);
        assertEquals(1, titles.size());
    }

}
