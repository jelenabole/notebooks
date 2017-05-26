package hr.tvz.bole.server.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.enums.DBStatus;
import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.other.mapper.NoteMapper;
import hr.tvz.bole.server.repository.NoteRepository;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.NoteForm;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	NoteRepository noteRepository;

	public List<Note> findAll() {
		List<Note> notes = noteRepository.findAll();

		return notes;
	}

	public List<Note> getAllPermitted(CurrentUser user) {
		if (user.isAdmin())
			return findAll();

		return noteRepository.findAllByUserIdAndStatus(user.getId(), DBStatus.ACTIVE);
	}

	public List<Note> getAllPermittedSorted(CurrentUser user) {
		if (user.isAdmin())
			return noteRepository.findAllByOrderByHeaderAsc();

		return noteRepository.findAllByUserIdAndStatusOrderByHeaderAsc(user.getId(),
				DBStatus.ACTIVE);
	}

	public List<Note> getAllPermittedSortedDesc(CurrentUser user) {
		if (user.isAdmin())
			return noteRepository.findAllByOrderByHeaderDesc();

		return noteRepository.findAllByUserIdAndStatusOrderByHeaderDesc(user.getId(),
				DBStatus.ACTIVE);
	}

	public List<Note> findAllActive() {
		return noteRepository.findAllByStatus(DBStatus.ACTIVE);
	}

	public Note findOne(Integer id) {
		return noteRepository.findById(id);
	}

	public List<Note> findByUser(Integer id) {
		return noteRepository.findByUserId(id);
	}

	public Note save(Note note) {
		return noteRepository.save(note);
	}

	public Note save(NoteForm noteForm) {
		Note note = NoteMapper.mapFormToNote(noteForm);
		noteRepository.save(note);
		return note;
	}

	public void delete(Integer id) {
		noteRepository.deleteById(id);
	}

	@Override
	public void deleteByNotebook(Integer id) {
		noteRepository.deleteByNotebookId(id);
	}

	@Override
	public void deleteByUser(Integer id) {
		noteRepository.deleteByUserId(id);
	}

	@Override
	public Integer getNumberOfNotesByNotebook(Integer id) {
		return noteRepository.countByNotebookId(id);
	}

	// @Override
	// public Integer getNumberOfNotesByNotebookAndByUser(Integer notebookId,
	// Integer userId) {
	// return noteRepository.countByNotebookIdAndUserId(notebookId, userId);
	// }

	public void changeNoteStatus(Integer id) {
		Note note = findOne(id);
		if (note.getStatus() == DBStatus.ACTIVE)
			note.setStatus(DBStatus.NOT_ACTIVE);
		else
			note.setStatus(DBStatus.ACTIVE);
		noteRepository.save(note);
	}

	@Override
	public NoteForm getOneAsForm(Integer id) {
		return NoteMapper.mapNoteToForm(findOne(id));
	}

	@Override
	public List<Note> getFilteredNotes(FilterForm filterForm, CurrentUser user) {
		logger.info("order by: " + filterForm.getOrderBy() + " - " + filterForm.getOrderDirection()
				+ " (" + filterForm.getSearchBy() + ")");

		// ako nema parametara, vrati sve za tog korisnika:
		// null se šalje (u orderBy) samo na onload:
		if (filterForm.getOrderBy() == null) {
			if (user.isAdmin())
				return findAll();
			else
				return getAllPermitted(user);
		}

		List<Note> notes;
		if (user.isAdmin())
			notes = getFilteredForAdmin(filterForm);
		else
			notes = getFilteredForUser(filterForm, user.getId());

		String search = filterForm.getSearchBy().toLowerCase();
		notes = notes.stream()
				.filter(e -> (e.getUser().getName().toLowerCase().contains(search)
						|| e.getUser().getSurname().toLowerCase().contains(search)
						|| e.getNotebook().getTitle().toLowerCase().contains(search)
						|| e.getHeader().toLowerCase().contains(search)
						|| e.getText().toLowerCase().contains(search)
				// || e.getMark().getName().toLowerCase().contains(search)
				// || e.getStatus().name().toLowerCase().contains(search)
				// || e.getImportance().name().toLowerCase().contains(search)
				)).collect(Collectors.toList());
		return notes;
	}

	private List<Note> getFilteredForUser(FilterForm filterForm, Integer userId) {
		switch (filterForm.getOrderDirection()) {
			case "asc":
				switch (filterForm.getOrderBy()) {
					case "notebook":
						return noteRepository.findAllByUserIdAndStatusOrderByNotebookAsc(userId,
								DBStatus.ACTIVE);
					case "header":
						return noteRepository.findAllByUserIdAndStatusOrderByHeaderAsc(userId,
								DBStatus.ACTIVE);
					case "text":
						return noteRepository.findAllByUserIdAndStatusOrderByTextAsc(userId,
								DBStatus.ACTIVE);
					case "importance":
						return noteRepository.findAllByUserIdAndStatusOrderByImportanceAsc(userId,
								DBStatus.ACTIVE);
					case "mark":
						return noteRepository.findAllByUserIdAndStatusOrderByMarkAsc(userId,
								DBStatus.ACTIVE);
					default:
						return noteRepository.findAllByUserIdAndStatus(userId, DBStatus.ACTIVE);

				}
			case "desc":
				switch (filterForm.getOrderBy()) {
					case "notebook":
						return noteRepository.findAllByUserIdAndStatusOrderByNotebookDesc(userId,
								DBStatus.ACTIVE);
					case "header":
						return noteRepository.findAllByUserIdAndStatusOrderByHeaderDesc(userId,
								DBStatus.ACTIVE);
					case "text":
						return noteRepository.findAllByUserIdAndStatusOrderByTextDesc(userId,
								DBStatus.ACTIVE);
					case "importance":
						return noteRepository.findAllByUserIdAndStatusOrderByImportanceDesc(userId,
								DBStatus.ACTIVE);
					case "mark":
						return noteRepository.findAllByUserIdAndStatusOrderByMarkDesc(userId,
								DBStatus.ACTIVE);
					default:
						return noteRepository.findAllByUserIdAndStatusOrderByIdDesc(userId,
								DBStatus.ACTIVE);
				}

		}
		logger.error("notebook filter (user) - return NULL");
		return null;
	}

	private List<Note> getFilteredForAdmin(FilterForm filterForm) {
		switch (filterForm.getOrderDirection()) {
			case "asc":
				switch (filterForm.getOrderBy()) {
					case "userName":
						return noteRepository.findAllByOrderByUserNameAscUserSurnameAsc();
					case "userSurname":
						return noteRepository.findAllByOrderByUserSurnameAscUserNameAsc();
					case "notebook":
						return noteRepository.findAllByOrderByNotebookTitleAsc();
					case "header":
						return noteRepository.findAllByOrderByHeaderAsc();
					case "text":
						return noteRepository.findAllByOrderByTextAsc();
					case "importance":
						return noteRepository.findAllByOrderByImportanceAsc();
					case "mark":
						return noteRepository.findAllByOrderByMarkAsc();
					case "status":
						return noteRepository.findAllByOrderByStatusAsc();
					default:
						return noteRepository.findAll();
				}
			case "desc":
				switch (filterForm.getOrderBy()) {
					case "userName":
						return noteRepository.findAllByOrderByUserNameDescUserSurnameDesc();
					case "userSurname":
						return noteRepository.findAllByOrderByUserSurnameDescUserNameDesc();
					case "notebook":
						return noteRepository.findAllByOrderByNotebookTitleDesc();
					case "header":
						return noteRepository.findAllByOrderByHeaderDesc();
					case "text":
						return noteRepository.findAllByOrderByTextDesc();
					case "importance":
						return noteRepository.findAllByOrderByImportanceDesc();
					case "mark":
						return noteRepository.findAllByOrderByMarkDesc();
					case "status":
						return noteRepository.findAllByOrderByStatusDesc();
					default:
						return noteRepository.findAllByOrderByIdDesc();
				}
		}
		logger.error("notebook filter (admin) - return NULL");
		return null;
	}

	@Override
	public Page<Note> getFirstFew(Integer numberOfNotes) {
		// dohvati prvih par
		return noteRepository.findAll(new PageRequest(0, numberOfNotes));
	}

}
