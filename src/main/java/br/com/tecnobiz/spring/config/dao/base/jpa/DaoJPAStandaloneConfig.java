package br.com.tecnobiz.spring.config.dao.base.jpa;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.tecnobiz.spring.config.dao.helpers.DataSourceConfigUtils;
import br.com.tecnobiz.spring.config.dao.profiles.StandaloneJPA;

/**
 * @see <a href=
 *      "http://stackoverflow.com/questions/17401583/optional-propertysource-location"
 *      >Optional @PropertySource location</a>
 * @author ricardozanini
 *
 */
@StandaloneJPA
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:/META-INF/standalone.properties", ignoreResourceNotFound = true)
public class DaoJPAStandaloneConfig extends AbstractDaoJPALocalConfig {

    public DaoJPAStandaloneConfig() {
	super();
    }

    @Override
    public DataSource dataSource() {
	return DataSourceConfigUtils.createDataSource(env);
    }

}
