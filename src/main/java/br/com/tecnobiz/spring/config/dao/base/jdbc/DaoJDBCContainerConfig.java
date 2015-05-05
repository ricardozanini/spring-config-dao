package br.com.tecnobiz.spring.config.dao.base.jdbc;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import br.com.tecnobiz.spring.config.dao.base.AbstractDaoConfig;
import br.com.tecnobiz.spring.config.dao.helpers.DataSourceConfigUtils;
import br.com.tecnobiz.spring.config.dao.profiles.ContainerJDBC;

/**
 * Configura��o utilizada em <em>Containers</em> com suporte JEE (WebSphere,
 * JBoss) para aplica��es que <strong>n�o</strong> utilizam JPA.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@ContainerJDBC
@EnableTransactionManagement
@Configuration
@PropertySource(value = "classpath:/META-INF/container.properties", ignoreResourceNotFound = true)
public class DaoJDBCContainerConfig extends AbstractDaoConfig {

    public DaoJDBCContainerConfig() {
	super();
    }

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
