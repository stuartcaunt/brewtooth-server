package com.brewtooth.server.persistence.dao;

import com.brewtooth.server.BrewToothConfiguration;
import com.brewtooth.server.BrewToothServer;
import com.brewtooth.server.service.MaltService;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

public class MaltDAOTest {

	@Inject
	private MaltService maltService;

	@ClassRule
	public static final DropwizardAppRule<BrewToothConfiguration> RULE = new DropwizardAppRule<BrewToothConfiguration>(BrewToothServer.class, ResourceHelpers.resourceFilePath("brewtooth-server-test.yml"));

	@Before
	public void setUpInjector() {
	}

	@Test
	public void myServiceShouldInject() {
		Assert.assertNotNull(maltService);
	}

}
