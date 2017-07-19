package com.brewtooth.server.persistence.dao;


import com.brewtooth.server.BrewToothServer;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public abstract class GenericDAO<T> {

	private static final Logger log = LoggerFactory.getLogger(GenericDAO.class);

	private Class classType;

	protected Provider<EntityManager> entityManagerProvider;

	@SuppressWarnings("unchecked")
	public GenericDAO(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
		this.classType  = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Returns all entities of the generic type
	 * @return All entities
	 */
	public List<T> getAll() {
		String className = classType.getSimpleName();
		String initial = className.toLowerCase().substring(0, 1);
		String queryString = "select " + initial + " from " + className + " " + initial;

		TypedQuery<T> query = entityManagerProvider.get().createQuery(queryString, classType);
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
	public T persist(T entity) {
		this.entityManagerProvider.get().persist(entity);

		return entity;
	}

	/**
	 * Merges an entity
	 * @param entity The entity to merge
	 * @return The merged entity
	 */
	public T merge(T entity) {
		T merged = this.entityManagerProvider.get().merge(entity);
		return merged;
	}

	/**
	 * Deletes an entity
	 * @param t The entity to delete
	 */
	public void delete(T t) {
		// First merge the entity to ensure it is not detached
		T merged = this.merge(t);

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
		return this.getFirstEntity(Arrays.asList(parameterName), parameterValue);
	}

	/**
	 * Returns the first entity matching the given search parameters
	 * @param parameterNames The search parameter names
	 * @param parameters The search parameter values
	 * @return The retrieved entity
	 */
	protected <T> T getFirstEntity(List<String> parameterNames, Object ... parameters) {
		try {
			TypedQuery<T> query = this.getTypedQuery(parameterNames, parameters);

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
		return this.getEntities(Arrays.asList(parameterName), parameterValue);
	}

	/**
	 * Returns all entities matching the given search parameters
	 * @param parameterNames The search parameter names
	 * @param parameters The search parameter values
	 * @return The list of retrieved entities
	 */
	protected <T> List<T> getEntities(List<String> parameterNames, Object ... parameters) {
		try {
			TypedQuery<T> query = this.getTypedQuery(parameterNames, parameters);

			return query.getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Generic function to obtain a TypedQuery from a given list of parameters
	 * @param parameterNames The parameter names
	 * @param parameters The parameter values
	 * @return The TypedQuery
	 */
	private <T> TypedQuery<T> getTypedQuery(List<String> parameterNames, Object ... parameters) {
		String className = classType.getSimpleName();
		String initial = className.toLowerCase().substring(0, 1);

		// Build up query string from parameters
		String queryString = "select " + initial + " from " + className + " " + initial + " where";
		for (int i = 0; i < parameterNames.size(); i++) {
			String name = parameterNames.get(i);
			Object value = parameters[i];

			queryString += " (" + initial + "." + name +
					(value == null ? " is null)" : (" = :" + name + ")")) +
					((i < parameterNames.size() - 1) ? " and" : "");
		}
		queryString += " order by " + initial + ".id asc";

		log.debug(queryString);

		// Generate the query
		TypedQuery<T> query = entityManagerProvider.get().createQuery(queryString, classType);

		// Set the query parameters
		for (int i = 0; i < parameterNames.size(); i++) {
			String name = parameterNames.get(i);
			Object value = parameters[i];

			if (value != null) {
				query.setParameter(name, value);
			}
		}

		return query;
	}

}
