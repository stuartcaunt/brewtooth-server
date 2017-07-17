package com.brewtooth.server.persistence.dao;

import com.brewtooth.server.domain.Malt;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

@Singleton
public class MaltDAO extends AbstractDAO<Malt> {

	@Inject
	public MaltDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Malt getById(Long id) {
		Query query = currentSession().createQuery("from Malt as m where m.id = :id")
			.setParameter("id", id);

		return this.uniqueResult(query);
	}

	public List<Malt> getAll() {
		return this.list(currentSession().createQuery("from Malt"));
	}
}
