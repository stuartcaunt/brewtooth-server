package com.brewtooth.server.util;

import com.google.inject.persist.Transactional;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class DatabaseHelper {

	Logger logger = Logger.getLogger(DatabaseHelper.class);

    @Transactional
    public void dropAllData() {
        EntityManager em = StartHelper.getInstance(EntityManager.class);
		em.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT NO CHECK").executeUpdate();
    }
}
