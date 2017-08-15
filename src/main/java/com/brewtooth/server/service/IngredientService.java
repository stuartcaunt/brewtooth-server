package com.brewtooth.server.service;

import com.brewtooth.server.domain.Ingredient;
import com.brewtooth.server.persistence.IngredientDAO;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class IngredientService<T extends Ingredient> {

	private static final Logger log = LoggerFactory.getLogger(IngredientService.class);

	private IngredientDAO<T> dao;
	private Class classType;

	public IngredientService(final IngredientDAO<T> dao) {
		this.dao = dao;
		this.classType = dao.getEntityClass();
	}

	public Class getEntityClass() {
		return this.classType;
	}

	/**
	 * Returns an entity of type T based on their Id
	 * @param id The Id of the entity of type T
	 * @return The entity of type T corresponding to the Id
	 */
	public T getById(Long id) {
		return this.dao.getById(id);
	}

	/**
	 * Persists the given entity of type T and ensures we do not create duplicates.
	 *  - if the id is set we assume we have an already persisted object and the object is updated
	 *  - If the entity of type T data match an existing entity of type T then we know it is already persisted
	 * @param entity The entity of type T to be persisted
	 * @return The persisted entity of type T
	 */
	@Transactional
	public synchronized T save(T entity) {
		T integratedT = null;

		// Check if it is a new object
		if (entity.getId() == null) {
			// Determine if the object already exists
			integratedT = this.dao.getByDetails(entity);
			if (integratedT != null) {
				String className = classType.getSimpleName();
				log.debug(className + " " + entity + " already present in the db under the id " + integratedT.getId());

			}  else {
				integratedT = this.dao.insert(entity);
			}

		} else {
			// merge
			integratedT = this.dao.update(entity);
		}

		return integratedT;
	}

	/**
	 * Return all entities of the generic type
	 * @return A list of all entities of the generic type
	 */
	public List<T> getAll() {
		return this.dao.getAll();
	}

	/**
	 * Deletes an entity with the generic type
	 * @param entity the entity to delete
	 */
	@Transactional
	public void delete(T entity) {
		this.dao.delete(entity);
	}

}
