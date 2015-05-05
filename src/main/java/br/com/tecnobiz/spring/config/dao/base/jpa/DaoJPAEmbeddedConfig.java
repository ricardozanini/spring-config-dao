package br.com.tecnobiz.spring.config.dao.base.jpa;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.tecnobiz.spring.config.dao.helpers.DataSourceConfigUtils;
import br.com.tecnobiz.spring.config.dao.profiles.EmbeddedJPA;

@EmbeddedJPA
@Configuration
@PropertySource(value = "classpath:/META-INF/embedded.properties", ignoreResourceNotFound = true)
@EnableTransactionManagement
public class DaoJPAEmbeddedConfig extends AbstractDaoJPALocalConfig {

	public DaoJPAEmbeddedConfig() {
		super();
	}

	@Override
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		return DataSourceConfigUtils.createEmbeddedDataSource(env);
	}
}
