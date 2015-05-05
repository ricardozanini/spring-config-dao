package br.com.tecnobiz.spring.config.dao;

import static com.google.common.base.Preconditions.checkState;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe base para facilitar a intera��o com o Spring JDBC. Veja os exemplos no
 * pacote de testes.
 * 
 * @see <a
 *      href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html">Spring
 *      JDBC</a>
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@Named
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractJDBCDao {

    @Inject
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    private SimpleJdbcInsert simpleJdbcInsert;

    public AbstractJDBCDao() {

    }

    @PostConstruct
    protected final void init() {
	checkState(this.dataSource != null,
		"Atencao! DataSource nao inicializado.");
	final JdbcTemplate rootJdbcTemplate = new JdbcTemplate(dataSource);

	this.jdbcTemplate = new NamedParameterJdbcTemplate(rootJdbcTemplate);
	this.simpleJdbcCall = new SimpleJdbcCall(rootJdbcTemplate);
	this.simpleJdbcInsert = new SimpleJdbcInsert(rootJdbcTemplate);
    }

    /**
     * Utilizado em cen�rios sem a inje��o autom�tica via {@link Inject}.
     * <p />
     * Normalmente n�o � utilizado. N�o utilize sem ter certeza do que est�
     * fazendo!
     * 
     * @param dataSource
     * @since 1.0.0
     */
    public final void setDataSource(final DataSource dataSource) {
	this.dataSource = dataSource;
    }

    protected final NamedParameterJdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    protected final SimpleJdbcCall getSimpleJdbcCall() {
	return simpleJdbcCall;
    }

    protected final SimpleJdbcInsert getSimpleJdbcInsert() {
	return simpleJdbcInsert;
    }

}
