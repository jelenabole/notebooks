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
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.PasswordGenerator;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.repository.UserRepository;

public class JdbcUserRepository implements UserRepository {

	private static Logger logger = LoggerFactory.getLogger(JdbcNoteRepository.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	String SELECT_USER = "SELECT id, name, surname, username, password, enabled FROM users";
	String SELECT_IF_ADMIN = "SELECT id, username, role FROM user_roles WHERE username=? AND role='ROLE_ADMIN'";

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(SELECT_USER, new UserRowMapper());
	}

	@Override
	public User findOne(String username) {
		return jdbcTemplate.queryForObject(SELECT_USER + " WHERE username=?", new UserRowMapper(), username);
	}

	@Override
	public boolean hasAdminRole(String username) {
		try {
			jdbcTemplate.queryForObject(SELECT_IF_ADMIN, new UserRoleRowMapper(), username);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	@Transactional
	public User save(User user) throws UserExistsException {
		if (checkIfUserExists(user.getUsername())) {
			//TODO - prevesti grešku:
			throw new UserExistsException("user exists: " + user.getUsername());
		}
		
		user.setPassword(PasswordGenerator.generatePassword(user.getPassword()));
		Integer userId = insertUserAndReturnId(user);
		logger.info("save new user - id: " + userId);
		user.setId(userId);
		
		insertUserRole(user, "ROLE_USER");
		return user;
	}

	private boolean checkIfUserExists(String username) {
		try {
			findOne(username);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

	private Integer insertUserAndReturnId(User user) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
		jdbcInsert.setGeneratedKeyName("id");
		
		//TODO - enabled polje zahardcodirano = TRUE
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", user.getName());
		args.put("surname", user.getSurname());
		args.put("username", user.getUsername());
		args.put("password", user.getPassword());
		args.put("enabled", true);
		Integer userId = jdbcInsert.executeAndReturnKey(args).intValue();
		
		return userId;
	}
	
	private Integer insertUserRole(User user, String userRole) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_roles");
		jdbcInsert.setGeneratedKeyName("id");
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", user.getUsername());
		args.put("role", userRole);
		Integer userId = jdbcInsert.executeAndReturnKey(args).intValue();
		
		return userId;
	}


	private static final class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String username = rs.getString("username");
			String password = rs.getString("password");
			boolean enabled = rs.getBoolean("enabled");

			return new User(id, name, surname, username, password, enabled);
		}
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
