package com.brewtooth.server.service;

import com.brewtooth.server.domain.Hop;
import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.persistence.HopDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class HopService {

	private static final Logger log = LoggerFactory.getLogger(HopService.class);

	private HopDAO hopDAO;

	@Inject
	public HopService(final HopDAO hopDAO) {
		this.hopDAO = hopDAO;
	}

	/**
	 * Returns a Hop based on their Id
	 * @param id The Id of the Hop
	 * @return The Hop corresponding to the Id
	 */
	public Hop getById(Long id) {
		return this.hopDAO.getById(id);
	}

	/**
	 * Persists the given Hop and ensures we do not create duplicates.
	 *  - if the id is set we assume we have an already persisted object and the object is updated
	 *  - If the Hop data match an existing Hop then we know it is already persisted
	 * @param hop The Hop to be persisted
	 * @return The persisted Hop
	 */
	@Transactional
	public synchronized Hop save(Hop hop) {
		Hop integratedHop = null;

		// Check if it is a new object
		if(hop.getId() == null) {
			// Determine if the object already exists
			integratedHop = this.hopDAO.getByDetails(hop);
			if (integratedHop != null) {
				log.debug("hop " + hop.getName() + " already present in the db under the id " + integratedHop.getId());

			} else {
				integratedHop = this.hopDAO.insert(hop);
			}
		} else {
			// merge
			integratedHop = this.hopDAO.update(hop);
		}

		return integratedHop;
	}

	/**
	 * Return all hops
	 * @return A list of all hops
	 */
	public List<Hop> getAll() {
		return this.hopDAO.getAll();
	}

	/**
	 * Deletes a hop
	 * @param hop the hop to delete
	 */
	@Transactional
	public void delete(Hop hop) {
		this.hopDAO.delete(hop);
	}
}
