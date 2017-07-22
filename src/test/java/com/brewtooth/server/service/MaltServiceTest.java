package com.brewtooth.server.service;

import com.brewtooth.server.PersistenceExtension;
import com.brewtooth.server.domain.GrainType;
import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.util.DatabaseHelper;
import com.brewtooth.server.util.StartHelper;
import com.google.inject.Inject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
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
	public void contextLoads() throws Exception {
		Assert.assertNotNull(this.maltService);
	}

	@Test
	@DisplayName("Test create and retrieve")
	public void createAndRetrieve() {
		Malt malt = new Malt("malt1", GrainType.BARLEY, 0.7f, 3.0f);
		maltService.save(malt);

		Assert.assertNotNull(malt.getId());

		List<Malt> malts = this.maltService.getAll();
		Assert.assertEquals(1, malts.size());

		Malt result = maltService.getById(malt.getId());

		Assert.assertEquals("malt1", result.getName());
		Assert.assertEquals(GrainType.BARLEY, result.getGrain());
		Assert.assertEquals(0.7f, result.getYield(), 0.001f);
		Assert.assertEquals(3.0f, result.getEBC(), 0.001f);
	}

	@Test
	@DisplayName("Test no duplicates")
	public void testSingleCreation() {
		Malt malt1a = new Malt("malt1", GrainType.BARLEY, 0.7f, 3.0f);
		Malt malt1b = new Malt("malt1", GrainType.BARLEY, 0.7f, 3.0f);

		malt1a = maltService.save(malt1a);

		Assert.assertNotNull(malt1a.getId());
		Long malt1aId = malt1a.getId();

		malt1b = maltService.save(malt1b);

		Assert.assertNotNull(malt1b.getId());
		Long malt1bId = malt1b.getId();

		Assert.assertEquals(malt1aId, malt1bId);
	}
}
