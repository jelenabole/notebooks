package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.model.Note;

public interface NoteRepository {

	public List<Note> findAll();

	public Note findOne(Integer id);
	
	Note save (Note note);
	
	Integer update (Note note);
	
	List<Note> findByUsername (String username);
	
	void delete (Integer id);

}
