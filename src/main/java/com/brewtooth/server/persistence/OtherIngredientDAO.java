package com.brewtooth.server.persistence;

import com.brewtooth.server.domain.OtherIngredient;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;

@Singleton
public class OtherIngredientDAO extends IngredientDAO<OtherIngredient> {

	@Inject
	public OtherIngredientDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}
}
