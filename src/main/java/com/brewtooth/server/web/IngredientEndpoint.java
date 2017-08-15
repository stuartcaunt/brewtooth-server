package com.brewtooth.server.web;

import com.brewtooth.server.domain.Ingredient;
import com.brewtooth.server.service.IngredientService;
import com.brewtooth.server.web.error.RestError;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class IngredientEndpoint<T extends Ingredient> {

	private static final Logger log = LoggerFactory.getLogger(IngredientEndpoint.class);

	@Inject
	private IngredientService<T> ingredientService;

	private Class entityType;

	@PostConstruct
	private void init() {
		this.entityType = ingredientService.getEntityClass();
	}

	/**
	 * Returns all entities of generic type
	 * @return List of Ts
	 */
	@GET
	@Produces("application/json")
	@Timed
	public Response getAll() {
		log.info("Getting all " + entityType.getSimpleName() + " entities");
		final List<T> entities = ingredientService.getAll();
		return Response.ok(entities).build();
	}

	/**
	 * Adds a new entity to the database
	 * @param entity The entity to add
	 * @return the persisted entity
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response create(T entity) {
		log.info("Adding " + entityType.getSimpleName() + " to db : " + entity);
		T integratedT = this.ingredientService.save(entity);

		return Response.ok(integratedT).build();
	}

	/**
	 * Returns a single entity from the DB
	 * @return The entity to return
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	@Timed
	public Response get(@PathParam("id") Long id) {
		log.info("Getting " + entityType.getSimpleName() + " with Id : " + id);
		T entity = this.ingredientService.getById(id);
		if (entity != null) {
			return Response.ok(entity).build();

		} else {
			return RestError.buildResponse(Response.Status.NOT_FOUND, "The " + entityType.getSimpleName() + " with Id " + id + " does not exist");
		}
	}

	/**
	 * Deletes a entity from the DB
	 */
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	@Timed
	public Response delete(@PathParam("id") Long id) {
		log.info("Deleting " + entityType.getSimpleName() + " with Id : " + id);
		T entity = this.ingredientService.getById(id);
		if (entity != null) {
			this.ingredientService.delete(entity);
			return Response.ok().build();

		} else {
			return RestError.buildResponse(Response.Status.NOT_FOUND, "The " + entityType.getSimpleName() + " with Id " + id + " does not exist");
		}
	}
	/**
	 * Update a entity in the database
	 * @param entity The entity to update
	 * @return the persisted entity
	 */
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	@Timed
	public Response update(@PathParam("id") Long id, T entity) {
		log.info("Updating " + entityType.getSimpleName() + " : " + entity);
		if (entity.getId() != null && entity.getId().equals(id)) {
			T integratedT = this.ingredientService.save(entity);

			if (integratedT != null) {
				return Response.ok(integratedT).build();

			} else {
				// error
				return RestError.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Failed to update the " + entityType.getSimpleName());
			}

		} else {
			return RestError.buildResponse(Response.Status.BAD_REQUEST, "The " + entityType.getSimpleName() + " ID does not correspond to the request parameter");
		}
	}

}
