package br.com.tecnobiz.spring.config.dao.base.jdbc;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.tecnobiz.spring.config.dao.base.AbstractDaoConfig;
import br.com.tecnobiz.spring.config.dao.helpers.DataSourceConfigUtils;
import br.com.tecnobiz.spring.config.dao.profiles.StandaloneJDBC;

/**
 * Utilizado em ambientes <em>standalone</em> (Jetty, Tomcat, Testes de
 * Integra��o, etc.) sem suporte � JPA.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@StandaloneJDBC
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:/META-INF/standalone.properties", ignoreResourceNotFound = true)
public class DaoJDBCStandaloneConfig extends AbstractDaoConfig {

    public DaoJDBCStandaloneConfig() {
	super();
    }

    @Override
    public DataSource dataSource() {
	return DataSourceConfigUtils.createDataSource(env);
    }

    @Override
    @Bean
    @DependsOn("dataSource")
    public PlatformTransactionManager transactionManager() {
	return new DataSourceTransactionManager(ds);
    }

}
