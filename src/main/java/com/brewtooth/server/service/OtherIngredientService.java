package com.brewtooth.server.service;

import com.brewtooth.server.domain.OtherIngredient;
import com.brewtooth.server.persistence.IngredientDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OtherIngredientService extends IngredientService<OtherIngredient> {

	@Inject
	public OtherIngredientService(final IngredientDAO<OtherIngredient> otherIngredientDAO) {
		super(otherIngredientDAO);
	}
}
