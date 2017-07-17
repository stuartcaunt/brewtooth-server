package com.brewtooth.server;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.health.ServerHealthCheck;
import com.brewtooth.server.persistence.BrewToothHibernateBundle;
import com.brewtooth.server.persistence.BrewToothHibernateModule;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class BrewToothServer extends Application<BrewToothConfiguration> {

	private static final Logger log = LoggerFactory.getLogger(BrewToothServer.class);

	public static void main(final String[] args) throws Exception {
		new BrewToothServer().run(args);
	}

	@Override
	public String getName() {
		return "BrewTooth server";
		}

	@Override
	public void initialize(final Bootstrap<BrewToothConfiguration> bootstrap) {
		final BrewToothHibernateBundle hibernateBundle = new BrewToothHibernateBundle();

		// register hbn bundle before guice to make sure factory initialized before guice context start
		bootstrap.addBundle(hibernateBundle);

		GuiceBundle<BrewToothConfiguration> guiceBundle = GuiceBundle.<BrewToothConfiguration>builder()
			.enableAutoConfig(
				"com.brewtooth.server.persistence.dao",
				"com.brewtooth.server.service",
				"com.brewtooth.server.web.resources"
			)
			.modules(new BrewToothHibernateModule(hibernateBundle))
			.build();

		bootstrap.addBundle(guiceBundle);

	}

	@Override
	public void run(final BrewToothConfiguration configuration, final Environment environment) {

		// Health checks
		environment.healthChecks().register("base", new ServerHealthCheck());

		// jersey config
//		environment.jersey().register(new WebserviceEndpoint(urlResolver));
	}

}
