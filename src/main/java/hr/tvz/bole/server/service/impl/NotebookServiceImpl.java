package hr.tvz.bole.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.other.mapper.NotebookMapper;
import hr.tvz.bole.server.repository.NotebookRepository;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.web.form.NotebookForm;

@Service
@Transactional
public class NotebookServiceImpl implements NotebookService {

	@Autowired
	NotebookRepository notebookRepository;
	@Autowired
	NoteService noteService;

	public List<Notebook> findAll() {
		return notebookRepository.findAll();
	}

	public List<Notebook> findAllWithNumberOfNotes() {
		List<Notebook> notebook = notebookRepository.findAll();
		notebook.forEach(e -> e.setNumberOfNotes(noteService.getNumberOfNotes(e.getTitle())));

		return notebook;
	}

	public List<Notebook> findAllWithNumberOfNotesForUser(String username) {
		List<Notebook> notebook = notebookRepository.findAll();
		notebook.forEach(e -> e.setNumberOfNotes(noteService.getNumberOfNotesForUser(e.getTitle(), username)));

		return notebook;
	}

	public Notebook findOneByTitle(String title) {
		return notebookRepository.findByTitle(title);
	}

	public Integer save(Notebook notebook) {
		// TODO - vraća se id:
		return notebookRepository.save(notebook).getId();
	}

	public Integer save(NotebookForm notebookForm) {
		// TODO - vraća se id:
		return notebookRepository.save(NotebookMapper.mapFormToNotebook(notebookForm)).getId();
	}

	public Integer update(Notebook notebook) {
		//TODO - promijenjeno u save:
		return notebookRepository.save(notebook).getId();
	}

	public Integer updateWithoutTitle(NotebookForm notebookForm) {
		// TODO - mapiranje obrisano - samo slanje parametara:
		return notebookRepository.updateWithoutTitle(notebookForm.getId(), notebookForm.getDescription());
	}

	public Integer update(NotebookForm notebookForm) {
		//TODO - promjenenjeno u save - vraćen id:
		return notebookRepository.save(NotebookMapper.mapFormToNotebook(notebookForm)).getId();
	}

	public void delete(Integer id) {
		noteService.deleteByNotebook(id);
		notebookRepository.deleteById(id);
	}

}
