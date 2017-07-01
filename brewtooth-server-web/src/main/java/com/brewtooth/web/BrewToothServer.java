package com.brewtooth.web;

import com.brewtooth.web.health.ServerHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BrewToothServer extends Application<BrewToothConfiguration> {

	public static void main(final String[] args) throws Exception {
		new BrewToothServer().run(args);
	}

	@Override
	public String getName() {
		return "BrewTooth server";
		}

	@Override
	public void initialize(final Bootstrap<BrewToothConfiguration> bootstrap) {
	}

	@Override
	public void run(final BrewToothConfiguration configuration, final Environment environment) {

		// Health checks
		environment.healthChecks().register("base", new ServerHealthCheck());

		// jersey config
//		environment.jersey().register(new WebserviceEndpoint(urlResolver));
	}

}
