package com.brewtooth.server.web;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.service.MaltService;
import com.brewtooth.server.web.error.RestError;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/malts")
public class MaltEndpoint {

	private static final Logger log = LoggerFactory.getLogger(MaltEndpoint.class);

	@Inject
	private MaltService maltService;

	/**
	 * Returns all malts
	 * @return List of Malts
	 */
	@GET
	@Produces("application/json")
	@Timed
	public Response getAll() {
		log.info("Getting all malts");
		final List<Malt> malts = maltService.getAll();
		return Response.ok(malts).build();
	}

	/**
	 * Adds a new malt to the database
	 * @param malt The malt to add
	 * @return the persisted malt
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response create(Malt malt) {
		log.info("Adding malt to db : " + malt);
		Malt integratedMalt = this.maltService.save(malt);

		return Response.ok(integratedMalt).build();
	}

	/**
	 * Returns a single malt from the DB
	 * @return The malt to return
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	@Timed
	public Response get(@PathParam("id") Long id) {
		log.info("Getting malt with Id : " + id);
		Malt malt = this.maltService.getById(id);
		if (malt != null) {
			return Response.ok(malt).build();

		} else {
			return RestError.buildResponse(Response.Status.NOT_FOUND, "The malt with Id " + id + " does not exist");
		}
	}

	/**
	 * Update a malt in the database
	 * @param malt The malt to update
	 * @return the persisted malt
	 */
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response update(@PathParam("id") Long id, Malt malt) {
		log.info("Updating malt : " + malt);
		if (malt.getId() != null && malt.getId().equals(id)) {
			Malt integratedMalt = this.maltService.save(malt);

			if (integratedMalt != null) {
				return Response.ok(integratedMalt).build();

			} else {
				// error
				return RestError.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Failed to update the malt");
			}

		} else {
			return RestError.buildResponse(Response.Status.BAD_REQUEST, "The malt ID does not correspond to the request parameter");
		}
	}


}
