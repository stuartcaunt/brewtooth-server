package com.brewtooth.server;

import com.brewtooth.server.domain.Hop;
import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.domain.Yeast;
import com.brewtooth.server.persistence.HopDAO;
import com.brewtooth.server.persistence.IngredientDAO;
import com.brewtooth.server.persistence.MaltDAO;
import com.brewtooth.server.persistence.YeastDAO;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class BrewtoothModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<IngredientDAO<Malt>>() {}).to(MaltDAO.class);
		bind(new TypeLiteral<IngredientDAO<Hop>>() {}).to(HopDAO.class);
		bind(new TypeLiteral<IngredientDAO<Yeast>>() {}).to(YeastDAO.class);
	}
}
