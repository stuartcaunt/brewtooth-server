package com.brewtooth.server.dao;

import com.brewtooth.server.domain.Malt;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class MaltDAO extends GenericDAO<Malt> {

	@Inject
	public MaltDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}
}
