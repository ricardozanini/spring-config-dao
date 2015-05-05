package br.com.tecnobiz.spring.config.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @see AbstractJPADao
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 * @param <E>
 * @param <I>
 */
public interface Dao<E extends Serializable, I> {

	E findById(I id);

	E create(E entity);

	/**
	 * <em>merge will return attached instance letting the given one unchanged.</em>
	 * 
	 * @see <a
	 *      href="http://stackoverflow.com/questions/22459233/hibernate-update-vs-jpa-merge-on-detached-instance">hibernate
	 *      update vs JPA merge on detached instance</a>
	 * @param entity
	 * @return A entidade atualizada
	 */
	E update(E entity);

	void remove(E entity);

	List<E> findAll();

}
