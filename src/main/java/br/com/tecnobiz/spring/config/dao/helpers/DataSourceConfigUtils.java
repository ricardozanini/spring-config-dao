package br.com.tecnobiz.spring.config.dao.helpers;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.jolbox.bonecp.BoneCPDataSource;

import static com.google.common.base.Strings.emptyToNull;

/**
 * Helper to access commons methods
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
public final class DataSourceConfigUtils {

	private DataSourceConfigUtils() {

	}

	/**
	 * Creates a H2 database to use in unit tests.
	 * 
	 */
	public static DataSource createEmbeddedDataSource(Environment env) {
		final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		final String loadScripts = env.getProperty(PropKeys.DB_LOAD_SCRIPTS);

		builder.setType(EmbeddedDatabaseType.H2);

		if (emptyToNull(loadScripts) != null) {
			builder.addScript(loadScripts);
		}

		return builder.build();
	}

	/**
	 * Creates a {@link DataSource} instance using BoneCP for stand alone
	 * configurations. If you intend to use a container, use that pool instead.
	 * 
	 * <p />
	 * <em>If you want to use something mature and performant (e.g. not Apache
	 * DBCP), use BoneCP.</em>
	 * 
	 * @see <a href="http://jolbox.com">BoneCP</a>
	 * @see <a
	 *      href="http://jolbox.com/bonecp/downloads/site/apidocs/com/jolbox/bonecp/BoneCPConfig.html">BoneCPConfig</a>
	 * @see <a
	 *      href="http://stackoverflow.com/questions/9745165/what-pooled-data-source-should-i-use-for-spring-3-1-0-hibernate-4-0-1-final-an">What
	 *      pooled data source should I use?</a>
	 */
	public static DataSource createDataSource(Environment env) {
		final BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(env.getProperty(PropKeys.DB_DRIVER));
		dataSource.setJdbcUrl(env.getProperty(PropKeys.DB_URL));
		dataSource.setUsername(env.getProperty(PropKeys.DB_USER));
		dataSource.setPassword(env.getProperty(PropKeys.DB_PASS));
		dataSource.setMaxConnectionsPerPartition(Integer.parseInt(env
				.getProperty(PropKeys.DB_MAX_CONN)));
		dataSource.setConnectionTestStatement(env
				.getProperty(PropKeys.DB_VAL_QUERY));

		return dataSource;
	}

	/**
	 * Get a {@link DataSource} reference from JNDI.
	 * 
	 * @throws NamingException
	 *             in case you don't set the right property or your JNDI haven't
	 *             initialized yet.
	 */
	public static DataSource getDataSourceFromJNDI(Environment env) {
		try {
			return (DataSource) new InitialContext().lookup(env
					.getProperty(PropKeys.JNDI_DS));
		} catch (NamingException e) {
			throw new IllegalStateException("DataSource not found.", e);
		}
	}

}
