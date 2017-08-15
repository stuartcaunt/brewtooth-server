package com.brewtooth.server;

import com.brewtooth.server.domain.*;
import com.brewtooth.server.persistence.*;
import com.brewtooth.server.service.*;
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
		bind(new TypeLiteral<IngredientDAO<Sugar>>() {}).to(SugarDAO.class);
		bind(new TypeLiteral<IngredientDAO<OtherIngredient>>() {}).to(OtherIngredientDAO.class);
	}

	private void bindServices() {
		bind(new TypeLiteral<IngredientService<Malt>>() {}).to(MaltService.class);
		bind(new TypeLiteral<IngredientService<Hop>>() {}).to(HopService.class);
		bind(new TypeLiteral<IngredientService<Yeast>>() {}).to(YeastService.class);
		bind(new TypeLiteral<IngredientService<Sugar>>() {}).to(SugarService.class);
		bind(new TypeLiteral<IngredientService<OtherIngredient>>() {}).to(OtherIngredientService.class);
	}
}
