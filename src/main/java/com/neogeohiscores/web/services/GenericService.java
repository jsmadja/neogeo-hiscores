package com.neogeohiscores.web.services;

import org.apache.tapestry5.ioc.annotations.Inject;

public abstract class GenericService<T> {

    @Inject
    protected org.hibernate.Session session;

    private Class<T> clazz;

    public GenericService(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findById(long id) {
        return (T) session.load(clazz, id);
    }

    public T store(T t) {
        return (T) session.merge(t);
    }

}
