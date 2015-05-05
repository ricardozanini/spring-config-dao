package br.com.tecnobiz.spring.config.dao.base.jdbc;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.tecnobiz.spring.config.dao.base.ProfileBasedDaoConfig;
import br.com.tecnobiz.spring.config.dao.base.SupportTestDaoConfig;

@ActiveProfiles("embedded-jdbc")
@ContextConfiguration(classes = { ProfileBasedDaoConfig.class })
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDaoJDBCEmbeddedConfig {

	@Inject
	private DataSource dataSource;

	@Test
	public void testDataSource() {
		SupportTestDaoConfig.assertConnection(dataSource);
	}

}
