package hr.tvz.bole.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import hr.tvz.bole.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

	public List<Note> findAll();

	public List<Note> findAllByStatusTrue();

	public Note findById(Integer id);

	public List<Note> findByUserId(Integer userId);

	@SuppressWarnings("unchecked")
	public Note save(Note note);

	// public void update(Note note);
	@Modifying
	@Transactional
	public void deleteById(Integer id);

	@Modifying
	@Transactional
	public void deleteByNotebookId(Integer id);

	@Modifying
	@Transactional
	public void deleteByUserId(Integer id);

	//TODO - ove dvije funkcije nisu implementirane
//	@Query("SELECT COUNT() Notes SET description=:description WHERE id = :id")
//	public Integer getNumberOfNotes(String title);

	// XXX - nepotrebno?!
//	@Query("UPDATE notebooks SET description=:description WHERE id = :id")
//	public Integer getNumberOfNotesForUser(String title, String username);
}
