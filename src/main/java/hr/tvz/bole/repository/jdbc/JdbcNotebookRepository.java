package hr.tvz.bole.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.repository.NotebookRepository;

public class JdbcNotebookRepository implements NotebookRepository {

	private JdbcTemplate jdbcTemplate;

	static final String SELECT_NOTEBOOKS = "SELECT * FROM notebooks";
	static final String UPDATE_NOTEBOOK = "UPDATE notebooks SET title=?, description=? WHERE id = ?";
	static final String DELETE_NOTEBOOK = "DELETE FROM notebooks WHERE id = ?";
	static final String UPDATE_NOTEBOOK_WITHOUT_TITLE = "UPDATE notebooks SET description=? WHERE id = ?";

	public JdbcNotebookRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Notebook> findAll() {
		return jdbcTemplate.query(SELECT_NOTEBOOKS, new NotebookRowMapper());
	}
	
	@Override
	public Notebook findOne(Integer id) {
		return jdbcTemplate.queryForObject(SELECT_NOTEBOOKS + " WHERE id = ?", new NotebookRowMapper(), id);
	}

	@Override
	public Notebook findOneByTitle(String title) {
		return jdbcTemplate.queryForObject(SELECT_NOTEBOOKS + " WHERE title=?", new NotebookRowMapper(), title);
	}

	@Override
	public Integer save(Notebook notebook) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("notebooks");
		jdbcInsert.setGeneratedKeyName("id");

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("title", notebook.getTitle());
		args.put("description", notebook.getDescription());

		return jdbcInsert.executeAndReturnKey(args).intValue();
	}

	@Override
	public Integer update(Notebook notebook) {
		return jdbcTemplate.update(UPDATE_NOTEBOOK, notebook.getTitle(), notebook.getDescription(), notebook.getId());
	}
	
	@Override
	public Integer updateWithoutTitle(Notebook notebook) {
		return jdbcTemplate.update(UPDATE_NOTEBOOK_WITHOUT_TITLE, notebook.getDescription(), notebook.getId());
	}

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update(DELETE_NOTEBOOK, id);
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
