package com.brewtooth.server;

import com.brewtooth.server.domain.Hop;
import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.domain.Yeast;
import com.brewtooth.server.persistence.HopDAO;
import com.brewtooth.server.persistence.IngredientDAO;
import com.brewtooth.server.persistence.MaltDAO;
import com.brewtooth.server.persistence.YeastDAO;
import com.brewtooth.server.service.HopService;
import com.brewtooth.server.service.IngredientService;
import com.brewtooth.server.service.MaltService;
import com.brewtooth.server.service.YeastService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class BrewtoothModule extends AbstractModule {

	@Override
	protected void configure() {
		this.bindDAOs();
		this.bindServices();
	}

	private void bindDAOs() {
		bind(new TypeLiteral<IngredientDAO<Malt>>() {}).to(MaltDAO.class);
		bind(new TypeLiteral<IngredientDAO<Hop>>() {}).to(HopDAO.class);
		bind(new TypeLiteral<IngredientDAO<Yeast>>() {}).to(YeastDAO.class);
	}

	private void bindServices() {
		bind(new TypeLiteral<IngredientService<Malt>>() {}).to(MaltService.class);
		bind(new TypeLiteral<IngredientService<Hop>>() {}).to(HopService.class);
		bind(new TypeLiteral<IngredientService<Yeast>>() {}).to(YeastService.class);
	}
}
