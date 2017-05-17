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

	// sort Asc - Admin:
	public List<Note> findAllByOrderByUserNameAscUserSurnameAsc();

	public List<Note> findAllByOrderByUserSurnameAscUserNameAsc();

	public List<Note> findAllByOrderByNotebookTitleAsc();

	public List<Note> findAllByOrderByHeaderAsc();

	public List<Note> findAllByOrderByTextAsc();

	public List<Note> findAllByOrderByImportanceAsc();

	public List<Note> findAllByOrderByMarkAsc();

	public List<Note> findAllByOrderByStatusAsc();

	// sort Asc - User:
	public List<Note> findAllByUserIdAndStatusOrderByNotebookAsc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByHeaderAsc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByTextAsc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByImportanceAsc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByMarkAsc(Integer userId, DBStatus status);

	// sort Desc - Admin:
	public List<Note> findAllByOrderByIdDesc();

	public List<Note> findAllByOrderByUserNameDescUserSurnameDesc();

	public List<Note> findAllByOrderByUserSurnameDescUserNameDesc();

	public List<Note> findAllByOrderByNotebookTitleDesc();

	public List<Note> findAllByOrderByHeaderDesc();

	public List<Note> findAllByOrderByTextDesc();

	public List<Note> findAllByOrderByImportanceDesc();

	public List<Note> findAllByOrderByMarkDesc();

	public List<Note> findAllByOrderByStatusDesc();

	// sort Desc - User:
	public List<Note> findAllByUserIdAndStatusOrderByIdDesc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByNotebookDesc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByHeaderDesc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByTextDesc(Integer userId, DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByImportanceDesc(Integer userId,
			DBStatus status);

	public List<Note> findAllByUserIdAndStatusOrderByMarkDesc(Integer userId, DBStatus status);

	// filter - search by:
	public List<Note> findAllByUserNameContainingIgnoreCaseOrUserSurnameContainingIgnoreCaseOrNotebookTitleContainingIgnoreCaseOrHeaderContainingIgnoreCaseOrTextContainingIgnoreCase(
			String str1, String str2, String str3, String str4, String str5);

}
