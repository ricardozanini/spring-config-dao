package br.com.tecnobiz.spring.config.dao.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Agregador das configura��es da <code>DAO</code>. Pode ser importado por
 * qualquer classe de configura��o. Apenas a infra-estrutura espec�fica do
 * <code>profile</code> definido no ambiente ser� configurada.
 * <p/>
 * Para escolher o <code>profile</code> desejado, basta definir a propriedade
 * <code>spring.profiles.active</code> no ambiente em quest�o.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @see <a href="http://www.mkyong.com/spring/spring-profiles-example/">Spring
 *      Profiles</a>
 */
@Configuration
@ComponentScan({ "br.com.tecnobiz.spring.config.dao.base.jpa",
	"br.com.tecnobiz.spring.config.dao.base.jdbc" })
public class ProfileBasedDaoConfig {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ProfileBasedDaoConfig.class);

    @Inject
    protected AbstractDaoConfig abstractDaoConfig;

    public ProfileBasedDaoConfig() {

    }

    @PostConstruct
    public void init() {
	// @formatter:off
	LOGGER.debug("[init] Configuracao do banco de dados finalizada.");
	checkNotNull(this.abstractDaoConfig, "Configuracao da camada de banco de dados nao inicializada.");
	LOGGER.debug("[init] Datasource configurado {}.", this.abstractDaoConfig.dataSource());
	// @formatter:on
    }
}
