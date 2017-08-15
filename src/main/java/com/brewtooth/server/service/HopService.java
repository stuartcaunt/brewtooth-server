package com.brewtooth.server.service;

import com.brewtooth.server.domain.Hop;
import com.brewtooth.server.persistence.IngredientDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HopService extends IngredientService<Hop> {

	@Inject
	public HopService(final IngredientDAO<Hop> hopDAO) {
		super(hopDAO);
	}
}
