package com.brewtooth.server;

import com.brewtooth.server.util.DatabaseHelper;
import com.brewtooth.server.util.StartHelper;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.*;
import java.util.Optional;

public class PersistenceExtension implements TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback {

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(PersistenceExtension.class);

	private Injector getOrCreateInjector(ExtensionContext context) {
		Optional<Class<?>> testClass = context.getTestClass();
		ExtensionContext.Store store = context.getStore(NAMESPACE);
		return store.getOrComputeIfAbsent(testClass, key -> StartHelper.getInjector(), Injector.class);
	}

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
		StartHelper.init("src/test/resources/brewtooth-server-test.yml");
		getOrCreateInjector(context).injectMembers(testInstance);
	}

    @Override
    public void beforeEach(TestExtensionContext context) {
    }

    @Override
    public void afterEach(TestExtensionContext context) {
		StartHelper.getInstance(DatabaseHelper.class).dropAllData();
    }
}
