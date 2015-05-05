package br.com.tecnobiz.spring.config.dao.base;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import org.mockito.Mockito;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.jolbox.bonecp.BoneCPDataSource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public final class SupportTestDaoConfig {

	private SupportTestDaoConfig() {

	}

	public static void setUpJNDIContext() throws NamingException {
		final BoneCPDataSource ds = new BoneCPDataSource();
		ds.setUser("sa");
		ds.setPassword("sa");
		ds.setJdbcUrl("jdbc:h2:~/test");
		ds.setDriverClass("org.h2.Driver");

		final SimpleNamingContextBuilder builder = SimpleNamingContextBuilder
				.emptyActivatedContextBuilder();
		builder.bind("java:datasources/SpringConfigDAODS", ds);
		// n�o precisamos desses shenanigans :D
		builder.bind("java:emf/spring-config-dao-em",
				Mockito.mock(EntityManager.class));
		builder.bind("java:comp/UserTransaction",
				Mockito.mock(UserTransaction.class));
		builder.activate();
	}

	public static void assertConnection(DataSource dataSource) {
		assertNotNull(dataSource);
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			assertFalse(conn.isClosed());
		} catch (SQLException e) {
			fail(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					DataSourceUtils.doCloseConnection(conn, dataSource);
				} else {
					fail("Nao foi possivel recuperar a conexao.");
				}
			} catch (SQLException e) {
				fail(e.getMessage());
			}
		}
	}

}
