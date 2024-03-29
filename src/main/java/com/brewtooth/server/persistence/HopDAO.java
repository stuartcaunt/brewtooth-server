package com.brewtooth.server.persistence;

import com.brewtooth.server.domain.Hop;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;

@Singleton
public class HopDAO extends IngredientDAO<Hop> {

	@Inject
	public HopDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}
}
