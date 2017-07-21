package com.brewtooth.server.service;

import com.brewtooth.server.PersistenceExtension;
import com.brewtooth.server.domain.Malt;
import com.google.inject.Inject;
import org.junit.Assert;
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
