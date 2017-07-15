package com.brewtooth.server.persistence.dao;

import com.brewtooth.server.domain.Malt;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

@Singleton
public class MaltDAO extends AbstractDAO<Malt> {

	@Inject
	public MaltDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Malt> getAll() {
		return list(currentSession().createQuery("from Malt"));
	}
}
