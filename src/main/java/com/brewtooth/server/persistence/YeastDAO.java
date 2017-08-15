package com.brewtooth.server.persistence;

import com.brewtooth.server.domain.Yeast;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;

@Singleton
public class YeastDAO extends IngredientDAO<Yeast> {

	@Inject
	public YeastDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}
}
