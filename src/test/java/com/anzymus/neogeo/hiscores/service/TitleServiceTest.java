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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.anzymus.neogeo.hiscores.domain.Title;
import com.anzymus.neogeo.hiscores.success.FirstScoreTitleStrategy;
import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;

public class TitleServiceTest {

    @Mock
    EntityManager em;

    TitleService titleService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        titleService = new TitleService();
        titleService.em = em;
    }

    @Test
    public void should_strategy() {
        String label = "Once upon a time";
        String description = "Add your first score in neogeo-hiscores.";

        Title title = new Title();
        title.setLabel(label);
        title.setDescription(description);
        title.setClassname("com.anzymus.neogeo.hiscores.success.FirstScoreTitleStrategy");

        TypedQuery query = Mockito.mock(TypedQuery.class);
        when(query.getResultList()).thenReturn(Arrays.asList(title));

        when(em.createQuery(anyString(), any(Class.class))).thenReturn(query);

        Map<Title, TitleUnlockingStrategy> strategies = titleService.findAllStrategies();
        TitleUnlockingStrategy strategy = strategies.values().iterator().next();
        Title foundTitle = strategies.keySet().iterator().next();

        assertTrue(strategy instanceof FirstScoreTitleStrategy);
        assertEquals(label, foundTitle.getLabel());
        assertEquals(description, foundTitle.getDescription());
    }
}
