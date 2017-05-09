package hr.tvz.bole.server.repository;

import java.util.List;

import hr.tvz.bole.model.Note;

public interface NoteRepository {

	public List<Note> findAll();

	public Note findOne(Integer id);

	public List<Note> findByUser(Integer userId);

	public void save(Note note);

	public void update(Note note);

	public void delete(Integer id);

	public void deleteByNotebook(Integer id);

	public void deleteByUser(Integer id);

	public Integer getNumberOfNotes(String title);

	//XXX - nepotrebno?!
	public Integer getNumberOfNotesForUser(String title, String username);
}
