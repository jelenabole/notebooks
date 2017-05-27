package hr.tvz.bole.server.service.impl;

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
		notebookRepository.deleteById(id);
	}

	// TODO - napraviti sa upitom preko baze:
	private List<Notebook> getNumberOfNotes(List<Notebook> notebooks) {
		notebooks.forEach(
				e -> e.setNumberOfNotes(noteService.getNumberOfNotesByNotebook(e.getId())));
		return notebooks;
	}

	@Override
	public NotebookForm getOneAsForm(Integer id) {
		return NotebookMapper.mapNotebookToForm(findOne(id));
	}

	@Override
	public List<Notebook> getFilteredNotebooks(FilterForm filterForm) {
		logger.info("order by: " + filterForm.getOrderBy() + " - " + filterForm.getOrderDirection()
				+ " (" + filterForm.getSearchBy() + ")");

		List<Notebook> notebooks = findAllWithNumberOfNotes();

		// orderBy == nul samo na onload (vrati sve):
		if (filterForm.getOrderBy() == null) {
			return notebooks;
		}

		// return by expression:
		if (!filterForm.getSearchBy().isEmpty()) {
			notebooks = filter(notebooks, filterForm.getSearchBy().toLowerCase());
		}

		// sort by:
		if (!filterForm.getOrderBy().isEmpty()) {
			notebooks = orderBy(notebooks, filterForm.getOrderBy());
		}

		// reverse (if desc):
		if (filterForm.getOrderDirection().equals("desc")) {
			notebooks = reverseOrder(notebooks);
		}

		return notebooks;
	}

	// filter - filter by:
	private List<Notebook> filter(List<Notebook> notebooks, String search) {
		return notebooks.stream()
				.filter(e -> (e.getTitle().toLowerCase().contains(search)
						|| e.getDescription().toLowerCase().contains(search)))
				.collect(Collectors.toList());
	}

	// filter - order by:
	private List<Notebook> orderBy(List<Notebook> notebooks, String orderBy) {
		switch (orderBy) {
			case "title":
				return notebooks.stream()
						.sorted((e1, e2) -> e1.getTitle().compareToIgnoreCase(e2.getTitle()))
						.collect(Collectors.toList());
			case "description":
				return notebooks.stream().sorted(
						(e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
						.collect(Collectors.toList());
			case "numberOfNotes":
				return notebooks.stream()
						.sorted((e1, e2) -> e1.getNumberOfNotes().compareTo(e2.getNumberOfNotes()))
						.collect(Collectors.toList());
		}
		return notebooks;
	}

	// filter - reverse order:
	private List<Notebook> reverseOrder(List<Notebook> notebooks) {
		int size = notebooks.size() / 2;
		int k = notebooks.size() - 1;
		for (int i = 0; i < size; i++) {
			Notebook temp = notebooks.get(i);
			notebooks.set(i, notebooks.get(k));
			notebooks.set(k, temp);
			k--;
		}
		return notebooks;
	}

}
