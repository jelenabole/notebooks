package hr.tvz.bole.server.service;

import java.util.List;

import hr.tvz.bole.model.Note;

public interface NoteService {

	//TODO - promijenjeno da se dohvaćaju samo sa status active
	public List<Note> findAll();

	public Note findOne(Integer id);

	public List<Note> findByUser(Integer id);

	public void save(Note note);

	public void update(Note note);

	public void delete(Integer id);

	public void deleteByNotebook(Integer id);

	public void deleteByUser(Integer id);

	// unimplemented methods (2):
	public Integer getNumberOfNotes(String title);

	public Integer getNumberOfNotesForUser(String title, String username);

}
