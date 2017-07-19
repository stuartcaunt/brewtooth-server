package com.brewtooth.server.service;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.persistence.dao.MaltDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import java.util.List;

@Singleton
public class MaltService {

	private MaltDAO maltDAO;

	@Inject
	public MaltService(final MaltDAO maltDAO) {
		this.maltDAO = maltDAO;
	}

	@Transactional
	public List<Malt> getAll() {
		return this.maltDAO.getAll();
	}
}
