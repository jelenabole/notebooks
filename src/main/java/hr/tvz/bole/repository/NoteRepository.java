package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.model.Note;

public interface NoteRepository {

	public List<Note> findAll();

	public Note findOne(Integer id);

	public List<Note> findByUsername(String username);

	public Integer save(Note note);

	public Integer update(Note note);

	public void delete(Integer id);

	public void deleteByNotebook(String title);

	public void deleteByUsername(String username);

	public Integer getNumberOfNotes(String title);

	//XXX - nepotrebno?!
	public Integer getNumberOfNotesForUser(String title, String username);
}
