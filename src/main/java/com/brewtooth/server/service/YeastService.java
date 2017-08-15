package com.brewtooth.server.service;

import com.brewtooth.server.domain.Yeast;
import com.brewtooth.server.persistence.IngredientDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class YeastService extends IngredientService<Yeast> {

	@Inject
	public YeastService(final IngredientDAO<Yeast> yeastDAO) {
		super(yeastDAO);
	}
}
