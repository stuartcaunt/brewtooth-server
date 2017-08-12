package com.brewtooth.server.persistence;

import com.brewtooth.server.domain.Hop;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import java.util.Arrays;

@Singleton
public class HopDAO extends GenericDAO<Hop>{

	@Inject
	public HopDAO(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}

	public Hop getByHopDetails(Hop hop) {
		return this.getFirstEntity(Arrays.asList("name", "alphaAcid", "isPellet"), hop.getName(), hop.getAlphaAcid(), hop.getIsPellet());
	}


}
