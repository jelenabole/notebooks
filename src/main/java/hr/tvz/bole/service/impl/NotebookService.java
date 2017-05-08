package hr.tvz.bole.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.bole.form.NotebookForm;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.repository.NoteRepository;
import hr.tvz.bole.repository.NotebookRepository;

@Service
public class NotebookService {

	@Autowired
	NotebookRepository notebookRepository;
	@Autowired
	NoteRepository noteRepository;

	public List<Notebook> findAll() {
		return notebookRepository.findAll();
	}

	public List<Notebook> findAllWithNumberOfNotes() {
		List<Notebook> notebook = notebookRepository.findAll();
		notebook.forEach(e -> e.setNumberOfNotes(noteRepository.getNumberOfNotes(e.getTitle())));

		return notebook;
	}

	public List<Notebook> findAllWithNumberOfNotesForUser(String username) {
		List<Notebook> notebook = notebookRepository.findAll();
		notebook.forEach(e -> e.setNumberOfNotes(noteRepository.getNumberOfNotesForUser(e.getTitle(), username)));

		return notebook;
	}

	public Notebook findOneByTitle(String title) {
		return notebookRepository.findOneByTitle(title);
	}

	public Integer save(Notebook notebook) {
		return notebookRepository.save(notebook);
	}

	// TODO - mapper:
	public Integer save(NotebookForm notebookForm) {
		return notebookRepository.save(mapFormToNotebook(notebookForm));
	}

	public Integer update(Notebook notebook) {
		return notebookRepository.update(notebook);
	}

	public Integer updateWithoutTitle(NotebookForm notebookForm) {
		return notebookRepository.updateWithoutTitle(mapFormToNotebook(notebookForm));
	}

	// TODO - mapper
	public Integer update(NotebookForm notebookForm) {
		return notebookRepository.update(mapFormToNotebook(notebookForm));
	}

	public void delete(Integer id) {
		// TODO - brisanje bilježnice - dohvaćanje title-a (inače preko ID-a)
		noteRepository.deleteByNotebook(notebookRepository.findOne(id).getTitle());
		notebookRepository.delete(id);
	}

	// TODO - mapper
	private Notebook mapFormToNotebook(NotebookForm notebookForm) {
		return new Notebook(notebookForm.getId(), notebookForm.getTitle(), notebookForm.getDescription());
	}

}
