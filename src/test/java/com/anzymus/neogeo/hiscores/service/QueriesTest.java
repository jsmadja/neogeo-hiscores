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

package com.anzymus.neogeo.hiscores.service;

import static org.junit.Assert.assertEquals;

import javax.persistence.Query;

import org.junit.Test;
import org.mockito.Mockito;

public class QueriesTest {

    @Test
    public void should_get_count_when_result_is_integer() {
        Query query = Mockito.mock(Query.class);
        Mockito.when(query.getSingleResult()).thenReturn(new Integer(5));
        assertEquals(5, Queries.getCount(query));
    }

    @Test
    public void should_get_count_when_result_is_long() {
        Query query = Mockito.mock(Query.class);
        Mockito.when(query.getSingleResult()).thenReturn(new Long(5));
        assertEquals(5, Queries.getCount(query));
    }
}
