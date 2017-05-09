package hr.tvz.bole.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import hr.tvz.bole.enums.DBStatus;
import hr.tvz.bole.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

	public List<Note> findAll();

	public List<Note> findAllByStatus(DBStatus status);

	public List<Note> findAllByUserIdAndStatus(Integer userId, DBStatus status);

	// sortirane:
	public List<Note> findAllByOrderByHeaderAsc();

	public List<Note> findAllByUserIdAndStatusOrderByHeaderAsc(Integer userId, DBStatus status);

	public List<Note> findAllByOrderByHeaderDesc();

	public List<Note> findAllByUserIdAndStatusOrderByHeaderDesc(Integer userId, DBStatus status);

	public Note findById(Integer id);

	public List<Note> findByUserId(Integer userId);

	@SuppressWarnings("unchecked")
	public Note save(Note note);

	@Modifying
	@Transactional
	public void deleteById(Integer id);

	@Modifying
	@Transactional
	public void deleteByNotebookId(Integer id);

	@Modifying
	@Transactional
	public void deleteByUserId(Integer id);

	public Integer countByNotebookId(Integer id);

	// public Integer countByNotebookIdAndUserId(Integer notebookId, Integer
	// userId);

	// @Query("SELECT COUNT(u) FROM User u WHERE u.name=?1")
	// Long aMethodNameOrSomething(String name);

}
