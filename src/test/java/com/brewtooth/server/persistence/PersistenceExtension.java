package com.brewtooth.server.persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Optional;

public class PersistenceExtension implements TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback {

	private static final Namespace NAMESPACE = Namespace.create(PersistenceExtension.class);

	private Injector getOrCreateInjector(ExtensionContext context) {
		Optional<Class<?>> testClass = context.getTestClass();
		Store store = context.getStore(NAMESPACE);
		return store.getOrComputeIfAbsent(testClass, key -> createInjector(), Injector.class);
	}

	private Injector createInjector() {
		final BrewToothHibernateBundle hibernateBundle = new BrewToothHibernateBundle();

		return Guice.createInjector(new BrewToothHibernateModule(hibernateBundle));
	}


	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
		getOrCreateInjector(context).injectMembers(testInstance);
	}

	@Override
	public void beforeEach(TestExtensionContext context) {
	}

	@Override
	public void afterEach(TestExtensionContext context) {
	}
}
