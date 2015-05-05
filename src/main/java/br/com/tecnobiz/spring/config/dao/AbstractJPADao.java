package br.com.tecnobiz.spring.config.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkState;

/**
 * Classe base para ser extendida por qualquer implementa��o de DAO que utilize
 * JPA. J� implementa os elementos CRUD por padr�o.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 * @param <E>
 *            Objeto de Dom�nio ou Entidade respons�vel pela DAO
 * @param <I>
 *            Identificador da entidade.
 */
@Named
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractJPADao<E extends Serializable, I> implements
		Dao<E, I> {

	private final Class<E> entityClass;

	private EntityManager entityManager;

	protected AbstractJPADao(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public CriteriaQuery<E> getCriteriaQuery() {
		return this.entityManager.getCriteriaBuilder().createQuery(entityClass);
	}

	@PersistenceContext
	@Inject
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@PostConstruct
	protected void init() {
		checkState(this.entityManager != null,
				"Atencao! Entity Manager nao inicializado.");
	}

	public void remove(E entity) {
		this.entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		return this.entityManager.createQuery(
				String.format("from %s", this.entityClass.getName()))
				.getResultList();
	}

	public E findById(I id) {
		return this.entityManager.find(entityClass, id);
	}

	public E create(E entity) {
		this.entityManager.persist(entity);
		
		return entity;
	}

	public E update(E entity) {
		return this.entityManager.merge(entity);
	}

}
