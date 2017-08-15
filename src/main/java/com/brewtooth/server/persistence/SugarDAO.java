package com.brewtooth.server.persistence;

import com.brewtooth.server.domain.Sugar;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;

@Singleton
public class SugarDAO extends IngredientDAO<Sugar> {

	@Inject
	public SugarDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}
}
