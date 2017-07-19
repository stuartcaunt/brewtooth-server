package com.brewtooth.server.persistence;

import com.brewtooth.server.BrewToothConfiguration;
import com.brewtooth.server.BrewToothServer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrewToothHibernateModule extends AbstractModule {

	private static final Logger log = LoggerFactory.getLogger(BrewToothHibernateModule.class);

	@Override
	protected void configure() {
	}
//
//	@Provides
//	public SessionFactory provideSessionFactory(BrewToothConfiguration configuration, Environment environment) {
//
//		SessionFactory sessionFactory = this.hibernateBundle.getSessionFactory();
//		if (sessionFactory == null) {
//			try {
//				this.hibernateBundle.run(configuration, environment);
//
//				return this.hibernateBundle.getSessionFactory();
//
//			} catch (Exception e) {
//				log.error("Unable to run BrewToothHibernateModule");
//				return null;
//			}
//
//		} else {
//			return sessionFactory;
//		}
//	}
}
