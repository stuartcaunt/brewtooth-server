package com.brewtooth.server.service;

import com.brewtooth.server.domain.Sugar;
import com.brewtooth.server.persistence.IngredientDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SugarService extends IngredientService<Sugar> {

	@Inject
	public SugarService(final IngredientDAO<Sugar> sugarDAO) {
		super(sugarDAO);
	}
}
