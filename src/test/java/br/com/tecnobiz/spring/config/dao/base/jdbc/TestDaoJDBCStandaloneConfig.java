package br.com.tecnobiz.spring.config.dao.base.jdbc;

import java.sql.SQLException;

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

@ActiveProfiles("standalone-jdbc")
@ContextConfiguration(classes = { ProfileBasedDaoConfig.class })
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDaoJDBCStandaloneConfig {

	@Inject
	private DataSource dataSource;

	@Test
	public void testDataSource() throws SQLException {
		SupportTestDaoConfig.assertConnection(dataSource);
	}

}
