package hr.tvz.bole.server.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.NoteForm;

public interface NoteService {

	public List<Note> findAll();

	public Note findOne(Integer id);

	public List<Note> findByUser(Integer id);

	public List<Note> getAllPermitted(CurrentUser user);

	public List<Note> getAllPermittedSorted(CurrentUser user);

	public List<Note> getAllPermittedSortedDesc(CurrentUser user);

	public Note save(Note note);

	public Note save(NoteForm noteForm);

	public void delete(Integer id);

	public Integer getNumberOfNotesByNotebook(Integer id);

	public void changeNoteStatus(Integer id);

	// XXX - funkcije s mapiranjima (u forme):
	public NoteForm getOneAsForm(Integer id);

	// XXX - Ajax - filter notes:
	public List<Note> getFilteredNotes(FilterForm filterForm, CurrentUser currentUser);

	public Page<Note> getFirstFew(Integer numberOfNotes);

}
