package com.brewtooth.server.service;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.util.DatabaseHelper;
import com.brewtooth.server.util.StartHelper;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import javax.persistence.EntityManager;
import java.util.List;

public class MaltServiceTest {

	private EntityManager entityManager;
	private MaltService maltService;

	@BeforeClass
	public static void initClass() {
		StartHelper.init("src/test/resources/brewtooth-server-test.yml");
	}

	@AfterClass
	public static void finish() {
		StartHelper.getInstance(DatabaseHelper.class).dropAllData();
	}

	@Before
	public void startTx() {
		if (entityManager==null) {
			entityManager = StartHelper.getInstance(EntityManager.class);
		}
		entityManager.getTransaction().begin();

		this.maltService = StartHelper.getInstance(MaltService.class);
	}

	@After
	public void rollbackTx() {
		entityManager.getTransaction().commit();
	}

	@Test
	@DisplayName("Test Service")
	public void testService() {
		Assert.assertNotNull(this.maltService);

		List<Malt> malts = this.maltService.getAll();
		Assert.assertEquals(0, malts.size());
	}
}
