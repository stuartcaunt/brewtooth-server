package com.brewtooth.server.persistence.dao;

import com.brewtooth.server.domain.Malt;
import com.google.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class MaltDAO {

	@PersistenceContext
	private EntityManager em;

//	@Inject
//	public MaltDAO(final SessionFactory sessionFactory) {
//		super(sessionFactory);
//	}

	public Malt getById(Long id) {
		return em.find(Malt.class, id);
	}

	public List<Malt> getAll() {
		TypedQuery<Malt> query = em.createNamedQuery("Malt.findAll", Malt.class);
		return query.getResultList();
	}
}
