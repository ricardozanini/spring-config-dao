package br.com.tecnobiz.spring.config.dao.base.jpa;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import br.com.tecnobiz.spring.config.dao.base.AbstractDaoConfig;
import br.com.tecnobiz.spring.config.dao.helpers.DataSourceConfigUtils;
import br.com.tecnobiz.spring.config.dao.helpers.PropKeys;
import br.com.tecnobiz.spring.config.dao.profiles.ContainerJPA;

/**
 * Arquivo de configura��o em ambientes JEE compat�veis com gerenciamento de
 * recursos, factories de persist�ncia, JNDI e etc.
 * <p/>
 * Em ambientes como estes, o Spring utiliza desses recursos para realizar a
 * inje��o.
 * 
 * @see <a
 *      href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_Web_Framework_Kit/2/html-single/Spring_Developer_Guide/index.html">Red
 *      Hat - Spring Developer Guide</a>
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@ContainerJPA
@EnableTransactionManagement
@Configuration
@PropertySource(value = "classpath:/META-INF/container.properties", ignoreResourceNotFound = true)
public class DaoJPAContainerConfig extends AbstractDaoConfig {

    public DaoJPAContainerConfig() {
	super();
    }

    /**
     * O Entity Manager criado pelo ContainerJDBC. Recuperado via JNDI.
     * <p/>
     * <em>
     * In general, for implementing transaction-aware components, it is not
     * typical to access EntityManagerFactories directly (for example a service
     * that delegates to multiple DAOs that have to be enrolled in the same
     * transaction). This is true even if Spring supports injection into fields
     * annotated with @PersistenceContext when an EntityManagerFactory is
     * provided. Rather, components should access the JNDI-bound EntityManager,
     * as it is JTA-synchronized and will be shared with non-Spring components
     * that use JPA as well (for example EJBs).
     * </em>
     * 
     * @see <a
     *      href="http://www.swiftmind.com/de/2011/06/22/spring-3-1-m2-testing-with-configuration-classes-and-profiles/"></a>
     * @throws NamingException
     */
    @Bean(name = "entityManager")
    public EntityManager entityManager() throws NamingException {
	final Context ctx = new InitialContext();

	return (EntityManager) ctx.lookup(env.getProperty(PropKeys.JNDI_EMF));
    }

    // for�ando a inicializa��o depois do Hibernate.
    @Bean(name = "flyway")
    @DependsOn("entityManager")
    @Override
    public Flyway flyway() {
	return super.flyway();
    }

    /**
     * O DS s� � exposto por conformidade com a API. O acesso � feito via JTA do
     * container.
     * 
     * @throws NamingException
     */
    @Bean
    @Override
    public DataSource dataSource() {
	return DataSourceConfigUtils.getDataSourceFromJNDI(env);
    }

    @Bean
    @Override
    public PlatformTransactionManager transactionManager() {
	return new JtaTransactionManager();
    }

}
