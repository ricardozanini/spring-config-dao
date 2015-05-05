package br.com.tecnobiz.spring.config.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.tecnobiz.spring.config.dao.examples.ExemplosConfig;
import br.com.tecnobiz.spring.config.dao.examples.SuperHeroiDao;
import br.com.tecnobiz.spring.config.dao.examples.model.SuperHeroi;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@ActiveProfiles("embedded-jpa")
@ContextConfiguration(classes = { ExemplosConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class TestAbstractJPADao {

	@Inject
	@Named("superHeroiJPADaoImpl")
	private SuperHeroiDao superHeroiDao;

	@Test
	public void testConsultarTodos() {
		final List<SuperHeroi> herois = superHeroiDao.findAll();
		assertNotNull(herois);
		assertTrue(herois.isEmpty()); // n�o h� her�is na nossa base de dados.
		// :(
	}

	@Test
	public void testInserir() {
		final SuperHeroi heroi = new SuperHeroi();
		heroi.setNome("Homem Aranha");
		heroi.setSuperPoder("Escalar Paredes");

		superHeroiDao.create(heroi);

		assertTrue(heroi.getId() > 0);
	}

}