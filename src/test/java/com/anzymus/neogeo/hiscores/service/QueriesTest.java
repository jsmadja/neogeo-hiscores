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
