package com.brewtooth.server.persistence;


import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class GenericDAO<T> {

	private static final Logger log = LoggerFactory.getLogger(GenericDAO.class);
	private static String EPSILON = "0.0001";

	protected Class entityClass;
	protected Provider<EntityManager> entityManagerProvider;

	@SuppressWarnings("unchecked")
	public GenericDAO(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
		this.entityClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class getEntityClass() {
		return this.entityClass;
	}

	/**
	 * Returns all entities of the generic type
	 * @return All entities
	 */
	public List<T> getAll() {
		String className = entityClass.getSimpleName();
		String initial = className.toLowerCase().substring(0, 1);
		String queryString = "select " + initial + " from " + className + " " + initial;

		TypedQuery<T> query = entityManagerProvider.get().createQuery(queryString, entityClass);
		return query.getResultList();
	}

	/**
	 * Returns the entity with the specified Id
	 * @param id the entity id
	 * @return The entity
	 */
	public T getById(Long id) {
		return this.getFirstEntity("id", id);
	}

	/**
	 * Persists a new entity
	 * @param entity the Entity to persist
	 * @return The persisted entity
	 */
	public T insert(T entity) {
		this.entityManagerProvider.get().persist(entity);

		return entity;
	}

	/**
	 * Merges an entity
	 * @param entity The entity to merge
	 * @return The merged entity
	 */
	public T update(T entity) {
		T merged = this.entityManagerProvider.get().merge(entity);
		return merged;
	}

	/**
	 * Deletes an entity
	 * @param t The entity to delete
	 */
	public void delete(T t) {
		// First merge the entity to ensure it is not detached
		T merged = this.update(t);

		// Remove/delete the entity
		this.entityManagerProvider.get().remove(merged);
	}

	/**
	 * Returns the first entity matching the given search parameter
	 * @param parameterName The search parameter name
	 * @param parameterValue The search parameter value
	 * @return The retrieved entity
	 */
	protected <T> T getFirstEntity(String parameterName, Object parameterValue) {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put(parameterName, parameterValue);

		return this.getFirstEntity(parameterMap);
	}

	/**
	 * Returns the first entity matching the given search parameters
	 * @param parameterNames The search parameter names
	 * @param parameters The search parameter values
	 * @return The retrieved entity
	 */
	protected <T> T getFirstEntity(List<String> parameterNames, Object ... parameters) {
		Map<String, Object> parameterMap = new LinkedHashMap<>();
		for (int i = 0; i < parameterNames.size(); i++) {
			parameterMap.put(parameterNames.get(i), parameters[i]);
		}

		return this.getFirstEntity(parameterMap);
	}

	/**
	 * Returns the first entity matching the given search parameters
	 * @param parameters The parameters
	 * @return The retrieved entity
	 */
	protected <T> T getFirstEntity(Map<String, Object> parameters) {
		try {
			TypedQuery<T> query = this.getTypedQuery(parameters);

			// Single result
			query.setMaxResults(1);

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Returns all entities matching the given search parameter
	 * @param parameterName The search parameter name
	 * @param parameterValue The search parameter value
	 * @return The list of retrieved entities
	 */
	protected <T> List<T> getEntities(String parameterName, Object parameterValue) {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put(parameterName, parameterValue);

		return this.getEntities(parameterMap);
	}

	/**
	 * Returns all entities matching the given search parameters
	 * @param parameterNames The search parameter names
	 * @param parameters The search parameter values
	 * @return The list of retrieved entities
	 */
	protected <T> List<T> getEntities(List<String> parameterNames, Object ... parameters) {
		Map<String, Object> parameterMap = new LinkedHashMap<>();
		for (int i = 0; i < parameterNames.size(); i++) {
			parameterMap.put(parameterNames.get(i), parameters[i]);
		}

		return this.getEntities(parameterMap);
	}

	/**
	 * Returns all entities matching the given search parameters
	 * @param parameters The parameters
	 * @return The list of retrieved entities
	 */
	protected <T> List<T> getEntities(Map<String, Object> parameters) {
		try {
			TypedQuery<T> query = this.getTypedQuery(parameters);

			return query.getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Generic function to obtain a TypedQuery from a given map of parameters
	 * @param parameters The parameters
	 * @return The TypedQuery
	 */
	private <T> TypedQuery<T> getTypedQuery(Map<String, Object> parameters) {
		String className = entityClass.getSimpleName();
		String initial = className.toLowerCase().substring(0, 1);

		// Build up query string from parameters
		String queryString = "select " + initial + " from " + className + " " + initial + " where";
		List<String> parameterNames = new ArrayList<>(parameters.keySet());
		for (int i = 0; i < parameterNames.size(); i++) {
			String name = parameterNames.get(i);
			Object value = parameters.get(name);

			queryString += " (" + initial + "." + name;
			if (value == null) {
				queryString += " is null)";

			} else if (value instanceof Float || value instanceof Double) {
				queryString += " > :" + name + " - "  + EPSILON + ") and (" + initial + "." + name +" < :" + name + " + " + EPSILON + ")";

			} else {
				queryString += " = :" + name + ")";
			}

			if ((i < parameterNames.size() - 1)) {
				queryString += " and";
			}
		}
		queryString += " order by " + initial + ".id asc";

		log.debug(queryString);

		// Generate the query
		TypedQuery<T> query = entityManagerProvider.get().createQuery(queryString, entityClass);

		// Set the query parameters
		for (String name : parameters.keySet()) {
			Object value = parameters.get(name);

			if (value != null) {
				query.setParameter(name, value);
			}
		}

		return query;
	}

}
