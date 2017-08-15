package com.brewtooth.server.service;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.persistence.IngredientDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MaltService extends IngredientService<Malt> {

	@Inject
	public MaltService(final IngredientDAO<Malt> maltDAO) {
		super(maltDAO);
	}
}
