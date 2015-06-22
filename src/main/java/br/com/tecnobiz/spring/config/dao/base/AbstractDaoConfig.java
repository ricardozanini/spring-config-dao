package br.com.tecnobiz.spring.config.dao.base;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.tecnobiz.spring.config.dao.helpers.PropKeys;
import static com.google.common.base.Strings.emptyToNull;

/**
 * Classe base de configuração para a camada de dados.
 * <p/>
 * Algumas referências:
 * <ul>
 * <li>http://gordondickens.com/wordpress/2012/06/12/spring-3-1-environment-
 * profiles/</li>
 * <li>
 * http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/jdbc
 * /datasource/DriverManagerDataSource.html</li>
 * <li>http://docs.spring.io/spring/docs/3.1.x/javadoc-api/org/springframework/
 * context/annotation/Configuration.html</li>
 * <li>http://docs.spring.io/spring/docs/3.1.x/javadoc-api/org/springframework/
 * context/annotation/Profile.html</li>
 * <li>http://gordondickens.com/wordpress/2013/02/28/database-config-spring-3-2-
 * environment-profiles/comment-page-1/</li>
 * </ul>
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
public abstract class AbstractDaoConfig {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AbstractDaoConfig.class);

    @Inject
    protected Environment env;

    @Inject
    protected DataSource ds;

    public void setEnvironment(final Environment environment) {
	this.env = environment;
    }

    @Bean(destroyMethod = "close", name = "dataSource")
    public abstract DataSource dataSource();

    @Bean
    public abstract PlatformTransactionManager transactionManager();

    /**
     * @see <a href="http://flywaydb.org/documentation/api/">Flyway API</a>
     */
    @Order(0)
    @Bean(name = "flyway")
    @DependsOn("dataSource")
    public Flyway flyway() {
	final String scripts = env.getProperty(PropKeys.DB_MIG_SCRIPTS);
	final Flyway flyway = new Flyway();

	if (emptyToNull(scripts) == null) {
	    LOGGER.info("[flyway] Nao ha scripts para realizar a migracao. Cancelando.");
	} else {
	    LOGGER.debug("[flyway] Inicializando o schema do banco de dados.");
	    flyway.setDataSource(ds);
	    flyway.setLocations(scripts);
	    flyway.setBaselineOnMigrate(true);
	    // flyway.clean();
	    try {
		flyway.migrate();
	    } catch (FlywayException ex) {
		LOGGER.warn(
			"[flyway] Problemas com a migracao, tentando o repair",
			ex);
		flyway.repair();
	    }

	    LOGGER.debug("[flyway] Schema do banco de dados inicializado com sucesso.");
	}

	return flyway;
    }

}
