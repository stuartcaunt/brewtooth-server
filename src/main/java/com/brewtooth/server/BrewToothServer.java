package com.brewtooth.server;

import com.brewtooth.server.health.ServerHealthCheck;
import com.brewtooth.server.util.StartHelper;
import com.brewtooth.server.web.resources.MaltResource;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

public class BrewToothServer extends Application<BrewToothConfiguration> {

	private static final Logger log = LoggerFactory.getLogger(BrewToothServer.class);

	private GuiceBundle<BrewToothConfiguration> guiceBundle;

	public static void main(final String[] args) throws Exception {
		for (String arg : args) {
			if (arg.endsWith(".yml")) {
				StartHelper.setConfigFilename(arg);
			}
		}
		new BrewToothServer().run(args);
	}

	@Override
	public String getName() {
		return "BrewTooth server";
	}

	@Override
	public void initialize(final Bootstrap<BrewToothConfiguration> bootstrap) {
		BrewToothConfiguration configuration = StartHelper.createConfiguration(StartHelper.getConfigFilename());
		Properties jpaProperties = StartHelper.createPropertiesFromConfiguration(configuration);

		JpaPersistModule jpaPersistModule = new JpaPersistModule(StartHelper.JPA_UNIT);
		jpaPersistModule.properties(jpaProperties);

		guiceBundle = GuiceBundle.<BrewToothConfiguration> newBuilder()
			.addModule(jpaPersistModule)
			.enableAutoConfig(
				"com.brewtooth.persistence",
				"com.brewtooth.server",
				"com.brewtooth.resources")
			.setConfigClass(BrewToothConfiguration.class).build();

		bootstrap.addBundle(guiceBundle);
	}

	@Override
	public void run(final BrewToothConfiguration configuration, final Environment environment) {
		StartHelper.init(StartHelper.getConfigFilename());

		// !!! NEEDED !!! - otherwise the EntityManager is not correct for all the requests
		environment.servlets().addFilter("persistFilter", guiceBundle.getInjector().getInstance(PersistFilter.class)).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

		// Register resources
		environment.jersey().register(guiceBundle.getInjector().getInstance(MaltResource.class));

		// Health checks
		environment.healthChecks().register("base", new ServerHealthCheck());
	}

}
