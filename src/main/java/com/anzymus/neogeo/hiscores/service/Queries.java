package com.anzymus.neogeo.hiscores.service;

import javax.persistence.Query;

public class Queries {

    static long getCount(Query query) {
        try {
            return (Integer) query.getSingleResult();
        } catch (ClassCastException e) {
            return (Long) query.getSingleResult();
        }
    }

}
