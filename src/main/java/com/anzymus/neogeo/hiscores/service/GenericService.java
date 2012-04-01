package com.anzymus.neogeo.hiscores.service;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public abstract class GenericService<T> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> clazz;

	public GenericService() {}
	
	public GenericService(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T findById(long id) {
		return em.find(clazz, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T store(T t) {
		em.persist(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T merge(T t) {
		return em.merge(t);
	}

}
