package hr.tvz.bole.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.repository.RoleRepository;

public class JdbcRoleRepository implements RoleRepository {

	private static Logger logger = LoggerFactory.getLogger(JdbcRoleRepository.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcRoleRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	static final String SELECT_ALL_ROLES = "SELECT id, username, role FROM user_roles";
	static final String SELECT_ALL_BY_USER = SELECT_ALL_ROLES + " WHERE username=?";
	static final String SELECT_ROLE_BY_USER = SELECT_ALL_ROLES + " WHERE username=? AND role=?";
	static final String UPDATE_ROLE = "UPDATE user_roles SET username = ?, role = ? WHERE id = ?";
	static final String DELETE_ROLE = "DELETE FROM user_roles WHERE id = ?";
	static final String DELETE_ALL_BY_USER = "DELETE FROM user_roles WHERE username = ?";
	static final String DELETE_ROLE_BY_USER = "DELETE FROM user_roles WHERE username = ? AND role = ?";

	@Override
	public List<UserRole> findAll() {
		return jdbcTemplate.query(SELECT_ALL_ROLES, new UserRoleRowMapper());
	}

	@Override
	public List<UserRole> findAllByUsername(String username) {
		return jdbcTemplate.query(SELECT_ALL_BY_USER, new UserRoleRowMapper(), username);
	}

	@Override
	public UserRole findOne(String username, String role) {
		try {
			return jdbcTemplate.queryForObject(SELECT_ROLE_BY_USER, new UserRoleRowMapper(), username, role);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Integer save(UserRole role) throws RoleExistsForUser {
		logger.info("save new role - username: " + role.getUsername());

		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_roles");
		jdbcInsert.setGeneratedKeyName("id");

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", role.getUsername());
		args.put("role", role.getRole());
		Integer roleId = jdbcInsert.executeAndReturnKey(args).intValue();

		return roleId;
	}

	@Override
	public Integer update(UserRole role) {
		return jdbcTemplate.update(UPDATE_ROLE, role.getUsername(), role.getRole(), role.getId());
	}

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update(DELETE_ROLE, id);
	}

	@Override
	public void deleteAllByUsername(String username) {
		jdbcTemplate.update(DELETE_ALL_BY_USER, username);
	}

	@Override
	public void deleteRoleByUsername(String username, String role) {
		jdbcTemplate.update(DELETE_ROLE_BY_USER, username, role);
	}

	private static final class UserRoleRowMapper implements RowMapper<UserRole> {
		public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String name = rs.getString("username");
			String role = rs.getString("role");

			return new UserRole(id, name, role);
		}
	}
}
