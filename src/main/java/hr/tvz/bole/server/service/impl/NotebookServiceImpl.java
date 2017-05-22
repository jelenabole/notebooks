package hr.tvz.bole.server.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.other.mapper.NotebookMapper;
import hr.tvz.bole.server.repository.NotebookRepository;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.NotebookForm;

@Service
@Transactional
public class NotebookServiceImpl implements NotebookService {

	private static Logger logger = LoggerFactory.getLogger(NotebookServiceImpl.class);

	@Autowired
	NotebookRepository notebookRepository;
	@Autowired
	NoteService noteService;

	public List<Notebook> findAll() {
		return notebookRepository.findAll();
	}

	public List<Notebook> findAllWithNumberOfNotes() {
		List<Notebook> notebooks = notebookRepository.findAll();
		notebooks = getNumberOfNotes(notebooks);

		return notebooks;
	}
	
	public Notebook findOne(Integer id) {
		return notebookRepository.findById(id);
	}

	public Notebook save(Notebook notebook) {
		return notebookRepository.save(notebook);
	}

	public Notebook save(NotebookForm notebookForm) {
		return notebookRepository.save(NotebookMapper.mapFormToNotebook(notebookForm));
	}

	public void delete(Integer id) {
		noteService.deleteByNotebook(id);
		notebookRepository.deleteById(id);
	}

	@Override
	public List<Notebook> getFilteredNotebooks(FilterForm filterForm) {
		logger.info("order by: " + filterForm.getOrderBy() + " - " + filterForm.getOrderDirection()
				+ " (" + filterForm.getSearchBy() + ")");

		if (!filterForm.getSearchBy().isEmpty()) {
			List<Notebook> notebooks = notebookRepository
					.findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
							filterForm.getSearchBy(), filterForm.getSearchBy());
			return getNumberOfNotes(notebooks);
		}

		List<Notebook> notebooks = getSortedNotebooks(filterForm);

		// TODO - dodati number of notes, osim u slučaju sortiranja po toj
		// koloni (već napravljeno):
		// notebooks.forEach(e -> System.out.println(e));
		if (!filterForm.getOrderBy().equals("numberOfNotes")) {
			notebooks = getNumberOfNotes(notebooks);
		}
		return notebooks;
	}

	private List<Notebook> getSortedNotebooks(FilterForm filterForm) {
		switch (filterForm.getOrderDirection()) {
			case "asc":
				return getAscending(filterForm.getOrderBy());
			case "desc":
				return getDescending(filterForm.getOrderBy());
		}
		logger.error("notebook filter - return NULL");
		return null;
	}

	private List<Notebook> getAscending(String orderBy) {
		switch (orderBy) {
			case "title":
				return notebookRepository.findAllByOrderByTitleAsc();
			case "description":
				return notebookRepository.findAllByOrderByDescriptionAsc();
			case "numberOfNotes":
				Comparator<Notebook> byNumberOfNotes = Comparator
						.comparing(e -> e.getNumberOfNotes());
				List<Notebook> notebooks = notebookRepository.findAll();
				notebooks = getNumberOfNotes(notebooks);
				notebooks = notebooks.stream().sorted(byNumberOfNotes).collect(Collectors.toList());
				return notebooks;
			default:
				return notebookRepository.findAll();
		}
	}

	private List<Notebook> getDescending(String orderBy) {
		switch (orderBy) {
			case "title":
				return notebookRepository.findAllByOrderByTitleDesc();
			case "description":
				return notebookRepository.findAllByOrderByDescriptionDesc();
			case "numberOfNotes":
				Comparator<Notebook> byNumberOfNotes = Comparator
						.comparing(e -> e.getNumberOfNotes());
				List<Notebook> notebooks = notebookRepository.findAll();
				notebooks = getNumberOfNotes(notebooks);
				notebooks = notebooks.stream().sorted(byNumberOfNotes.reversed())
						.collect(Collectors.toList());
				return notebooks;
			default:
				return notebookRepository.findAllByOrderByIdDesc();
		}
	}

	// TODO - trenutno se ne koristi:
	private List<Notebook> getNumberOfNotes(List<Notebook> notebooks) {
		notebooks.forEach(
				e -> e.setNumberOfNotes(noteService.getNumberOfNotesByNotebook(e.getId())));
		return notebooks;
	}

}
