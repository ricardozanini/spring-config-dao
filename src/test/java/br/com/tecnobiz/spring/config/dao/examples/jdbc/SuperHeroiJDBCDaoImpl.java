package br.com.tecnobiz.spring.config.dao.examples.jdbc;

import java.util.List;

import javax.inject.Named;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.tecnobiz.spring.config.dao.AbstractJDBCDao;
import br.com.tecnobiz.spring.config.dao.examples.SuperHeroiDao;
import br.com.tecnobiz.spring.config.dao.examples.model.SuperHeroi;
import br.com.tecnobiz.spring.config.dao.profiles.EmbeddedJDBC;

@EmbeddedJDBC
// s� estamos especificando por causa dos testes, no projeto em si nao faca
// isso!
@Named
@Transactional(propagation = Propagation.MANDATORY)
public class SuperHeroiJDBCDaoImpl extends AbstractJDBCDao implements
		SuperHeroiDao {

	public SuperHeroiJDBCDaoImpl() {

	}

	@Override
	public SuperHeroi findById(Integer id) {
		final String sql = "SELECT id, nome, superPoder FROM SuperHeroi WHERE id = :id";
		final SqlParameterSource paramSource = new MapSqlParameterSource("id",
				id);

		return this.getJdbcTemplate().queryForObject(sql, paramSource,
				BeanPropertyRowMapper.newInstance(SuperHeroi.class));
	}

	@Override
	public SuperHeroi create(SuperHeroi e) {

		final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				e);
		final Number id = this.getSimpleJdbcInsert()
				.withTableName("SuperHeroi").usingGeneratedKeyColumns("id")
				.executeAndReturnKey(parameterSource);

		e.setId(id.intValue());
		
		return e;
	}

	@Override
	public void remove(SuperHeroi entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public SuperHeroi update(SuperHeroi entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuperHeroi> findAll() {
		final String sql = "SELECT id, nome, superPoder FROM SuperHeroi";

		return this.getJdbcTemplate().query(sql,
				BeanPropertyRowMapper.newInstance(SuperHeroi.class));
	}

	/*
	 * private static class SuperHeroiMapper implements RowMapper<SuperHeroi> {
	 * 
	 * @Override public SuperHeroi mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { final SuperHeroi superHeroi = new SuperHeroi();
	 * 
	 * superHeroi.setId(rs.getInt("id"));
	 * superHeroi.setNome(rs.getString("nome"));
	 * superHeroi.setSuperPoder(rs.getString("superPoder"));
	 * 
	 * return superHeroi; } }
	 */
}
