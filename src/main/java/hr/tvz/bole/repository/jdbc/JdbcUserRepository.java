package hr.tvz.bole.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import hr.tvz.bole.model.User;
import hr.tvz.bole.repository.RoleRepository;
import hr.tvz.bole.repository.UserRepository;

public class JdbcUserRepository implements UserRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	RoleRepository userRoleRepository;

	public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	static final String SELECT_USERS = "SELECT id, name, surname, username, email, password, enabled FROM users";
	static final String UPDATE_USER = "UPDATE users SET name=?, surname=?, email=? WHERE id = ?";
	static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
	static final String CHANGE_PASSWORD = "UPDATE users SET password=? WHERE id=?";

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(SELECT_USERS, new UserRowMapper());
	}

	@Override
	public User findOne(Integer id) {
		try {
			return jdbcTemplate.queryForObject(SELECT_USERS + " WHERE id=?", new UserRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public User findOneByUsername(String username) {
		try {
			return jdbcTemplate.queryForObject(SELECT_USERS + " WHERE username=?", new UserRowMapper(), username);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Integer save(User user) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
		jdbcInsert.setGeneratedKeyName("id");

		// TODO - enabled polje zahardcodirano = TRUE
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", user.getName());
		args.put("surname", user.getSurname());
		args.put("username", user.getUsername());
		args.put("email", user.getEmail());
		args.put("password", user.getPassword());
		args.put("enabled", true);

		return jdbcInsert.executeAndReturnKey(args).intValue();
	}

	@Override
	public Integer update(User user) {
		return jdbcTemplate.update(UPDATE_USER, user.getName(), user.getSurname(), user.getEmail(), user.getId());
	}

	@Override
	public Integer changePassword(Integer id, String password) {
		return jdbcTemplate.update(CHANGE_PASSWORD, password, id);
	}

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update(DELETE_USER, id);
	}

	private static final class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String username = rs.getString("username");
			String email = rs.getString("email");
			String password = rs.getString("password");
			boolean enabled = rs.getBoolean("enabled");

			return new User(id, name, surname, username, email, password, enabled);
		}
	}

}
