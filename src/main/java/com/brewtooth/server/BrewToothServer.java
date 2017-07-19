package com.brewtooth.server;

import com.brewtooth.server.health.ServerHealthCheck;
import com.brewtooth.server.util.StartHelper;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class BrewToothServer extends Application<BrewToothConfiguration> {

	private static final Logger log = LoggerFactory.getLogger(BrewToothServer.class);

	private GuiceBundle<BrewToothConfiguration> guiceBundle;

	public static void main(final String[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			if (args[i].endsWith(".yml")) {
				StartHelper.setConfigFilename(args[i]);
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
//			.addModule(new ToDoGuiceModule())
			.addModule(jpaPersistModule).enableAutoConfig(
				"com.brewtooth.persistence",
				"com.brewtooth.server",
				"com.brewtooth.resources")
			.setConfigClass(BrewToothConfiguration.class).build();

		bootstrap.addBundle(guiceBundle);

	}

	@Override
	public void run(final BrewToothConfiguration configuration, final Environment environment) {

		StartHelper.init(StartHelper.getConfigFilename());
//		environment.jersey().register(guiceBundle.getInjector().getInstance(AlbumsResource.class));

		// Health checks
		environment.healthChecks().register("base", new ServerHealthCheck());

		// jersey config
//		environment.jersey().register(new WebserviceEndpoint(urlResolver));
	}

}
