package com.brewtooth.server.service;

import com.brewtooth.server.BrewToothConfiguration;
import com.brewtooth.server.BrewToothServer;
import com.brewtooth.server.PersistenceExtension;
import com.brewtooth.server.domain.Malt;
import com.google.inject.Inject;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(PersistenceExtension.class)
public class MaltServiceTest {

	@Inject
	private MaltService maltService;

	@Test
	@DisplayName("Test Service")
	public void testService() {
		Assert.assertNotNull(this.maltService);

		List<Malt> malts = this.maltService.getAll();
		Assert.assertEquals(0, malts.size());
	}
}
