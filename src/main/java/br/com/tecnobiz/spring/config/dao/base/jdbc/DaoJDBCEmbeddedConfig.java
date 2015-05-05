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
import br.com.tecnobiz.spring.config.dao.profiles.EmbeddedJDBC;

@EmbeddedJDBC
@Configuration
@PropertySource(value = "classpath:/META-INF/embedded.properties", ignoreResourceNotFound = true)
@EnableTransactionManagement
public class DaoJDBCEmbeddedConfig extends AbstractDaoConfig {

    public DaoJDBCEmbeddedConfig() {
	super();
    }

    @Bean(destroyMethod = "shutdown")
    @Override
    public DataSource dataSource() {
	return DataSourceConfigUtils.createEmbeddedDataSource(env);
    }

    @Bean
    @DependsOn("dataSource")
    @Override
    public PlatformTransactionManager transactionManager() {
	return new DataSourceTransactionManager(ds);
    }

}
