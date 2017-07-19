//package com.brewtooth.server.persistence;
//
//import com.brewtooth.server.BrewToothConfiguration;
//import com.brewtooth.server.domain.Malt;
//import com.google.common.collect.ImmutableList;
//import io.dropwizard.db.PooledDataSourceFactory;
//import io.dropwizard.hibernate.HibernateBundle;
//import io.dropwizard.hibernate.SessionFactoryFactory;
//
//public class BrewToothHibernateBundle extends HibernateBundle<BrewToothConfiguration> {
//
//	public BrewToothHibernateBundle() {
//		super(Malt.class);
//	}
//
//	@Override
//	public PooledDataSourceFactory getDataSourceFactory(BrewToothConfiguration configuration) {
//		return null;
////		return configuration.getDataSourceFactory();
//	}
//
//}
