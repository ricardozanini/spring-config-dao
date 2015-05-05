package br.com.tecnobiz.spring.config.dao.examples;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.tecnobiz.spring.config.dao.base.ProfileBasedDaoConfig;

/**
 * Exemplo de uma factory de configura��o utilizando
 * {@link ProfileBasedDaoConfig} para determinar a configura��o da DAO.
 * <p/>
 * No seu ambiente, definir a propriedade <code>spring.active.profiles</code> de
 * acordo com o <em>profile</em> desejado.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({ "br.com.tecnobiz.spring.config.dao.examples.jpa",
		"br.com.tecnobiz.spring.config.dao.examples.jdbc" })
@Import(ProfileBasedDaoConfig.class)
public class ExemplosConfig {

	@Inject
	protected ProfileBasedDaoConfig daoConfig;

	public ExemplosConfig() {

	}

}
