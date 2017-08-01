package com.brewtooth.server.web;

import com.brewtooth.server.domain.Hop;
import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.service.HopService;
import com.brewtooth.server.web.error.RestError;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/hops")
public class HopEndpoint {

	private static final Logger log = LoggerFactory.getLogger(HopEndpoint.class);

	@Inject
	private HopService hopService;

	/**
	 * Returns all hops
	 * @return List of Hops
	 */
	@GET
	@Produces("application/json")
	@Timed
	public Response getAll() {
		log.info("Getting all hops");
		final List<Hop> hops = hopService.getAll();
		return Response.ok(hops).build();
	}

	/**
	 * Adds a new hop to the database
	 * @param hop The hop to add
	 * @return the persisted hop
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response create(Hop hop) {
		log.info("Adding hop to db : " + hop);
		Hop integratedHop = this.hopService.save(hop);

		return Response.ok(integratedHop).build();
	}

	/**
	 * Returns a single hop from the DB
	 * @return The hop to return
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	@Timed
	public Response get(@PathParam("id") Long id) {
		log.info("Getting hop with Id : " + id);
		Hop hop = this.hopService.getById(id);
		if (hop != null) {
			return Response.ok(hop).build();

		} else {
			return RestError.buildResponse(Response.Status.NOT_FOUND, "The hop with Id " + id + " does not exist");
		}
	}

	/**
	 * Update a hop in the database
	 * @param hop The hop to update
	 * @return the persisted hop
	 */
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response update(@PathParam("id") Long id, Hop hop) {
		log.info("Updating hop : " + hop);
		if (hop.getId() != null && hop.getId().equals(id)) {
			Hop integratedHop = this.hopService.save(hop);

			if (integratedHop != null) {
				return Response.ok(integratedHop).build();

			} else {
				// error
				return RestError.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Failed to update the hop");
			}

		} else {
			return RestError.buildResponse(Response.Status.BAD_REQUEST, "The hop ID does not correspond to the request parameter");
		}
	}

}
