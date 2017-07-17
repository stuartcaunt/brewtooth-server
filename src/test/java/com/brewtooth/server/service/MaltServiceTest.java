package com.brewtooth.server.service;

import com.brewtooth.server.BrewToothConfiguration;
import com.brewtooth.server.BrewToothServer;
import com.brewtooth.server.persistence.BrewToothHibernateBundle;
import com.brewtooth.server.persistence.BrewToothHibernateModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MaltServiceTest {

	@ClassRule
	public static final DropwizardAppRule<BrewToothConfiguration> RULE = new DropwizardAppRule<BrewToothConfiguration>(BrewToothServer.class, ResourceHelpers.resourceFilePath("brewtooth-server-test.yml"));

	@Before
	public void setUp() throws Exception {
		final BrewToothHibernateBundle hibernateBundle = new BrewToothHibernateBundle();

		// register hbn bundle before guice to make sure factory initialized before guice context start
		//bootstrap.addBundle(hibernateBundle);

		GuiceBundle<BrewToothConfiguration> guiceBundle = GuiceBundle.<BrewToothConfiguration>newBuilder()
			.enableAutoConfig(
				"com.brewtooth.server.dao",
				"com.brewtooth.server.service",
				"com.brewtooth.server.web.resources"
			)
			.addModule(new BrewToothHibernateModule(hibernateBundle))
			.setConfigClass(BrewToothConfiguration.class)
			.build();

		//bootstrap.addBundle(guiceBundle);

		Bootstrap bootstrap = mock(Bootstrap.class);

//		hibernateBundle.initialize(bootstrap);
		guiceBundle.initialize(bootstrap);

//		hibernateBundle.run(RULE.getConfiguration(), RULE.getEnvironment());
		guiceBundle.run(RULE.getConfiguration(), RULE.getEnvironment());
	}

	@After
	public void tearDown() {
		JerseyGuiceUtils.reset();
	}


	@Test
	@DisplayName("Test Service")
	public void testService() {


	}

}
