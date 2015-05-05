package br.com.tecnobiz.spring.config.dao.examples.jpa;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.tecnobiz.spring.config.dao.AbstractJPADao;
import br.com.tecnobiz.spring.config.dao.examples.SuperHeroiDao;
import br.com.tecnobiz.spring.config.dao.examples.model.SuperHeroi;
import br.com.tecnobiz.spring.config.dao.profiles.EmbeddedJPA;

@EmbeddedJPA
// s� estamos especificando por causa dos testes, no projeto em si nao faca
// isso!
@Named
@Transactional(propagation = Propagation.MANDATORY)
class SuperHeroiJPADaoImpl extends AbstractJPADao<SuperHeroi, Integer>
		implements SuperHeroiDao {

	public SuperHeroiJPADaoImpl() {
		super(SuperHeroi.class);
	}

}
