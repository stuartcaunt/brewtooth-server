package com.brewtooth.server.persistence;

import com.brewtooth.server.domain.Ingredient;
import com.google.inject.Provider;

import javax.persistence.EntityManager;

public abstract class IngredientDAO<T extends Ingredient> extends GenericDAO<T> {

	public IngredientDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}

	public T getByDetails(T type) {
		return this.getFirstEntity(type.getDetails());
	}
}
