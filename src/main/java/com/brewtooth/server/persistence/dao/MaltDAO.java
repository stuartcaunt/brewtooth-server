package com.brewtooth.server.persistence.dao;

import com.brewtooth.server.domain.Malt;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class MaltDAO extends GenericDAO<Malt> {

	@Inject
	public MaltDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}
}
