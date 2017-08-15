package com.brewtooth.server.service;

import com.brewtooth.server.domain.Yeast;
import com.brewtooth.server.persistence.YeastDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class YeastService {

	private static final Logger log = LoggerFactory.getLogger(YeastService.class);

	private YeastDAO yeastDAO;

	@Inject
	public YeastService(final YeastDAO yeastDAO) {
		this.yeastDAO = yeastDAO;
	}

	/**
	 * Returns a Yeast based on their Id
	 * @param id The Id of the Yeast
	 * @return The Yeast corresponding to the Id
	 */
	public Yeast getById(Long id) {
		return this.yeastDAO.getById(id);
	}

	/**
	 * Persists the given Yeast and ensures we do not create duplicates.
	 *  - if the id is set we assume we have an already persisted object and the object is updated
	 *  - If the Yeast data match an existing Yeast then we know it is already persisted
	 * @param yeast The Yeast to be persisted
	 * @return The persisted Yeast
	 */
	@Transactional
	public synchronized Yeast save(Yeast yeast) {
		Yeast integratedYeast = null;

		// Check if it is a new object
		if (yeast.getId() == null) {
			// Determine if the object already exists
			integratedYeast = this.yeastDAO.getByDetails(yeast);
			if (integratedYeast != null) {
				log.debug("yeast " + yeast.getName() + " already present in the db under the id " + integratedYeast.getId());

			}  else {
				integratedYeast = this.yeastDAO.insert(yeast);
			}

		} else {
			// merge
			integratedYeast = this.yeastDAO.update(yeast);
		}

		return integratedYeast;
	}

	/**
	 * Return all yeasts
	 * @return A list of all yeasts
	 */
	public List<Yeast> getAll() {
		return this.yeastDAO.getAll();
	}

	/**
	 * Deletes a yeast
	 * @param yeast the yeast to delete
	 */
	@Transactional
	public void delete(Yeast yeast) {
		this.yeastDAO.delete(yeast);
	}

}
