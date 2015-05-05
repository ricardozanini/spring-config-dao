package br.com.tecnobiz.spring.config.dao.base.jpa;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ClassUtils;

import br.com.tecnobiz.spring.config.dao.base.AbstractDaoConfig;
import br.com.tecnobiz.spring.config.dao.helpers.PropKeys;

import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

/**
 * Classe de configura��o do contexto Spring para implementa��es JPADao via JPA.
 * <p/>
 * Utilizada por servidores sem suporte � gest�o de recursos ou
 * <em>Standalones</em>, onde a cria��o e gerenciamento do contexto de
 * persist�ncia fica por conta do Spring.
 * 
 * @since 1.0.0
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * 
 * @see AbstractDaoConfig
 */
public abstract class AbstractDaoJPALocalConfig extends AbstractDaoConfig {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractDaoJPALocalConfig.class);
	
	public AbstractDaoJPALocalConfig() {
		super();
	}

	@Bean(name = "flyway")
	@DependsOn("entityManagerFactory")
	@Override
	public Flyway flyway() {
		return super.flyway();
	}

	@DependsOn("dataSource")
	@Bean(name = "entityManagerFactory")
	public AbstractEntityManagerFactoryBean entityManagerFactoryBean() {

		// hibernate.properties deve estar na raiz do classpath quando utilizar
		// essa factory

		// @formatter:off
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		final String pkgs = env.getProperty(PropKeys.JPA_ENTITY_PACKS);
	
		entityManagerFactoryBean.setDataSource(ds);
		entityManagerFactoryBean.setJpaVendorAdapter(getJpaVendorAdapterFromEnv());
		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
		
		// @formatter:on

		// n�o h� null check na factory do Spring. :(
		if (emptyToNull(pkgs) == null) {
			LOGGER.warn("[entityManagerFactoryBean] Nao ha pacotes para serem scaneados pelo Entity Manager criado pelo Spring.");
		} else {
			entityManagerFactoryBean.setPackagesToScan(pkgs);
		}

		return entityManagerFactoryBean;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Override
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(this.entityManagerFactoryBean()
				.getObject());
	}
	
	private Properties getAllProperties() {
		final Properties props = new Properties();
        
		for(Iterator<?> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource<?> propertySource = (PropertySource<?>) it.next();
            if (propertySource instanceof MapPropertySource) {
            	props.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
		
		return props;
	}
	
	private Properties getHibernateProperties() {
		final Properties all = this.getAllProperties();
		final Properties hibernate = new Properties();
		
		for (Entry<Object, Object> prop : all.entrySet()) {
			if(prop.getKey().toString().startsWith("hibernate")) {
				hibernate.put(prop.getKey(), prop.getValue());
			}
		}
		
		return hibernate;
	}

	private JpaVendorAdapter getJpaVendorAdapterFromEnv() {
		final String jpaVendor = env.getProperty(PropKeys.JPA_VENDOR);
		checkNotNull(emptyToNull(jpaVendor), "Eh preciso definir o JPA Vendor.");

		try {
			return (JpaVendorAdapter) ClassUtils.forName(jpaVendor,
					Thread.currentThread().getContextClassLoader())
					.newInstance();
		} catch (ReflectiveOperationException | LinkageError e) {
			throw new IllegalArgumentException(String.format(
					"Impossivel estanciar o JPA Vendor '%s'.", jpaVendor), e);
		}
	}
}
