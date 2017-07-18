package com.brewtooth.server.service;

import com.brewtooth.server.BrewToothConfiguration;
import com.brewtooth.server.BrewToothServer;
import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.persistence.BrewToothHibernateBundle;
import com.brewtooth.server.persistence.BrewToothHibernateModule;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MaltServiceTest {

	@ClassRule
	public static final DropwizardAppRule<BrewToothConfiguration> RULE = new DropwizardAppRule<BrewToothConfiguration>(BrewToothServer.class, ResourceHelpers.resourceFilePath("brewtooth-server-test.yml"));

	@Inject
	private MaltService maltService;

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
			.build();

		//bootstrap.addBundle(guiceBundle);

		Bootstrap bootstrap = mock(Bootstrap.class);

		Environment environment = new Environment("test env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);

		guiceBundle.initialize(bootstrap);

		hibernateBundle.run(RULE.getConfiguration(), environment);
		guiceBundle.run(RULE.getConfiguration(), environment);
		guiceBundle.getInjector().injectMembers(this);
	}

	@After
	public void tearDown() {
	}


	@Test
	@DisplayName("Test Service")
	public void testService() {
		Assert.assertNotNull(this.maltService);

		List<Malt> malts = this.maltService.getAll();
		Assert.assertEquals(0, malts.size());
	}



}
