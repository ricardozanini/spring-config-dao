package br.com.tecnobiz.spring.config.dao.base.jpa;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.tecnobiz.spring.config.dao.base.ProfileBasedDaoConfig;
import br.com.tecnobiz.spring.config.dao.base.SupportTestDaoConfig;

@ActiveProfiles("container-jpa")
@ContextConfiguration(classes = { ProfileBasedDaoConfig.class })
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDaoJPAContainerConfig {

    @Inject
    private DataSource dataSource;

    @BeforeClass
    public static void setUpJNDI() throws IllegalStateException,
	    NamingException {
	SupportTestDaoConfig.setUpJNDIContext();
    }

    @Test
    public void testDataSource() {
	SupportTestDaoConfig.assertConnection(dataSource);
    }

}
