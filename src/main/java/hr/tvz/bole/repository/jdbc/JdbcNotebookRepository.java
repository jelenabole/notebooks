package hr.tvz.bole.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.repository.NotebookRepository;

public class JdbcNotebookRepository implements NotebookRepository {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcNotebookRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Notebook> findAll() {
		return jdbcTemplate.query("SELECT * FROM notebooks", new NotebookRowMapper());
	}

	@Override   public Notebook findOne(String title) {   
		return jdbcTemplate.queryForObject("SELECT title, description FROM notebooks WHERE title=?",
				new NotebookRowMapper(), title);  
		}

	private static final class NotebookRowMapper implements RowMapper<Notebook> {
		public Notebook mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String description = rs.getString("description");
			return new Notebook(id, title, description);
		}
	}
}
