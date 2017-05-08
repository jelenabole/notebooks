package hr.tvz.bole.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;
import hr.tvz.bole.repository.NoteRepository;

public class JdbcNoteRepository implements NoteRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcNoteRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// XXX - user = username, notebook = title
	static final String SELECT_NOTE = "SELECT notes.id, notes.header, notes.text, notes.important, notes.mark, "
			+ "notebooks.id as notebookId, notebooks.title, notebooks.description, "
			+ "users.id as userId, users.name, users.surname, users.username " + "FROM notes, notebooks, users "
			+ "WHERE notes.notebook = notebooks.title AND notes.user = users.username";
	static final String SELECT_NOTE_BY_ID = SELECT_NOTE + " AND notes.id = ?";
	static final String SELECT_NOTE_BY_USERNAME = SELECT_NOTE + " AND notes.user = ?";
	static final String UPDATE_NOTE = "UPDATE notes SET header = ?, text = ?, user = ?, notebook = ?, important = ?, mark = ? WHERE id = ?";
	static final String DELETE_NOTE = "DELETE FROM notes WHERE id = ?";
	static final String DELETE_NOTE_BY_NOTEBOOK = "DELETE FROM notes WHERE notebook = ?";
	static final String DELETE_NOTE_BY_USERNAME = "DELETE FROM notes WHERE user = ?";
	static final String COUNT_NOTES = "SELECT COUNT(*) FROM notes WHERE notebook = ?";

	@Override
	public List<Note> findAll() {
		return jdbcTemplate.query(SELECT_NOTE, new NoteRowMapper());
	}

	@Override
	public Note findOne(Integer id) {
		try {
			return jdbcTemplate.queryForObject(SELECT_NOTE_BY_ID, new NoteRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Note> findByUsername(String username) {
		return jdbcTemplate.query(SELECT_NOTE_BY_USERNAME, new NoteRowMapper(), username);
	}
	
	@Override
	public Integer getNumberOfNotes(String title) {
		return jdbcTemplate.queryForObject(COUNT_NOTES, Integer.class, title);
	}
	
	@Override
	public Integer getNumberOfNotesForUser(String title, String username) {
		return jdbcTemplate.queryForObject(COUNT_NOTES + " AND user = ?", Integer.class, title, username);
	}

	@Override
	public Integer save(Note note) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("notes");
		jdbcInsert.setGeneratedKeyName("id");

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("header", note.getHeader());
		args.put("text", note.getText());
		args.put("user", note.getUser().getUsername());
		args.put("notebook", note.getNotebook().getTitle());
		args.put("important", note.getImportant());
		args.put("mark", note.getMark());

		return jdbcInsert.executeAndReturnKey(args).intValue();
	}

	@Override
	public Integer update(Note note) {
		return jdbcTemplate.update(UPDATE_NOTE, note.getHeader(), note.getText(), note.getUser().getUsername(),
				note.getNotebook().getTitle(), note.getImportant(), note.getMark(), note.getId());
	}

	@Override
	public void delete(Integer id) {
		jdbcTemplate.update(DELETE_NOTE, id);
	}

	@Override
	public void deleteByNotebook(String title) {
		jdbcTemplate.update(DELETE_NOTE_BY_NOTEBOOK, title);
	}

	@Override
	public void deleteByUsername(String username) {
		jdbcTemplate.update(DELETE_NOTE_BY_USERNAME, username);
	}

	private static final class NoteRowMapper implements RowMapper<Note> {
		public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
			Integer id = rs.getInt("id");
			String header = rs.getString("header");
			String text = rs.getString("text");
			Boolean important = rs.getBoolean("important");
			String mark = rs.getString("mark");

			int notebookId = rs.getInt("notebookId");
			String title = rs.getString("title");
			String description = rs.getString("description");
			Notebook notebook = new Notebook(notebookId, title, description);

			Integer userId = rs.getInt("userId");
			String username = rs.getString("username");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			User user = new User(userId, username, name, surname);

			Note note = new Note(id, user, notebook, header, text, important, mark);
			return note;
		}
	}
}
