package com.brewtooth.server.service;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.persistence.MaltDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class MaltService {

	private static final Logger log = LoggerFactory.getLogger(MaltService.class);

	private MaltDAO maltDAO;

	@Inject
	public MaltService(final MaltDAO maltDAO) {
		this.maltDAO = maltDAO;
	}

	/**
	 * Returns a Malt based on their Id
	 * @param id The Id of the Malt
	 * @return The Malt corresponding to the Id
	 */
	public Malt getById(Long id) {
		return this.maltDAO.getById(id);
	}

	/**
	 * Persists the given Malt and ensures we do not create duplicates.
	 *  - if the id is set we assume we have an already persisted object and the object is updated
	 *  - If the Malt data match an existing Malt then we know it is already persisted
	 * @param malt The Malt to be persisted
	 * @return The persisted Malt
	 */
	@Transactional
	public synchronized Malt save(Malt malt) {
		Malt integratedMalt = null;

		// Check if it is a new object
		if (malt.getId() == null) {
			// Determine if the object already exists
			integratedMalt = this.maltDAO.getByDetails(malt);
			if (integratedMalt != null) {
				log.debug("malt " + malt.getName() + " already present in the db under the id " + integratedMalt.getId());

			}  else {
				integratedMalt = this.maltDAO.insert(malt);
			}

		} else {
			// merge
			integratedMalt = this.maltDAO.update(malt);
		}

		return integratedMalt;
	}

	/**
	 * Return all malts
	 * @return A list of all malts
	 */
	public List<Malt> getAll() {
		return this.maltDAO.getAll();
	}

	/**
	 * Deletes a malt
	 * @param malt the malt to delete
	 */
	@Transactional
	public void delete(Malt malt) {
		this.maltDAO.delete(malt);
	}

}
