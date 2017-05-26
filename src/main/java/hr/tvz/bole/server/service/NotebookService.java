package hr.tvz.bole.server.service;

import java.util.List;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.NotebookForm;

public interface NotebookService {

	public List<Notebook> findAll();

	public List<Notebook> findAllWithNumberOfNotes();

	public Notebook findOne(Integer id);

	public Notebook save(Notebook notebook);

	public Notebook save(NotebookForm notebookForm);

	public void delete(Integer id);

	public List<Notebook> getFilteredNotebooks(FilterForm filterForm);

	public NotebookForm getOneAsForm(Integer id);

}
