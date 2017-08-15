package com.brewtooth.server.web;

import com.brewtooth.server.domain.Yeast;
import com.brewtooth.server.service.YeastService;
import com.brewtooth.server.web.error.RestError;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/yeasts")
public class YeastEndpoint {

	private static final Logger log = LoggerFactory.getLogger(YeastEndpoint.class);

	@Inject
	private YeastService yeastService;

	/**
	 * Returns all yeasts
	 * @return List of Yeasts
	 */
	@GET
	@Produces("application/json")
	@Timed
	public Response getAll() {
		log.info("Getting all yeasts");
		final List<Yeast> yeasts = yeastService.getAll();
		return Response.ok(yeasts).build();
	}

	/**
	 * Adds a new yeast to the database
	 * @param yeast The yeast to add
	 * @return the persisted yeast
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response create(Yeast yeast) {
		log.info("Adding yeast to db : " + yeast);
		Yeast integratedYeast = this.yeastService.save(yeast);

		return Response.ok(integratedYeast).build();
	}

	/**
	 * Returns a single yeast from the DB
	 * @return The yeast to return
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	@Timed
	public Response get(@PathParam("id") Long id) {
		log.info("Getting yeast with Id : " + id);
		Yeast yeast = this.yeastService.getById(id);
		if (yeast != null) {
			return Response.ok(yeast).build();

		} else {
			return RestError.buildResponse(Response.Status.NOT_FOUND, "The yeast with Id " + id + " does not exist");
		}
	}

	/**
	 * Deletes a yeast from the DB
	 */
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	@Timed
	public Response delete(@PathParam("id") Long id) {
		log.info("Deleting yeast with Id : " + id);
		Yeast yeast = this.yeastService.getById(id);
		if (yeast != null) {
			this.yeastService.delete(yeast);
			return Response.ok().build();

		} else {
			return RestError.buildResponse(Response.Status.NOT_FOUND, "The yeast with Id " + id + " does not exist");
		}
	}

	/**
	 * Update a yeast in the database
	 * @param yeast The yeast to update
	 * @return the persisted yeast
	 */
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response update(@PathParam("id") Long id, Yeast yeast) {
		log.info("Updating yeast : " + yeast);
		if (yeast.getId() != null && yeast.getId().equals(id)) {
			Yeast integratedYeast = this.yeastService.save(yeast);

			if (integratedYeast != null) {
				return Response.ok(integratedYeast).build();

			} else {
				// error
				return RestError.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Failed to update the yeast");
			}

		} else {
			return RestError.buildResponse(Response.Status.BAD_REQUEST, "The yeast ID does not correspond to the request parameter");
		}
	}


}
