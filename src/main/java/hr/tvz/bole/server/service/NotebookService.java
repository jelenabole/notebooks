package hr.tvz.bole.server.service;

import java.util.List;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.web.form.NotebookForm;

public interface NotebookService {

	public List<Notebook> findAll();

	public List<Notebook> findAllWithNumberOfNotes();

	public List<Notebook> findAllWithNumberOfNotesForUser(String username);

	public Notebook findOneByTitle(String title);

	public Integer save(Notebook notebook);

	public Integer save(NotebookForm notebookForm);

	public Integer update(Notebook notebook);

	public Integer updateWithoutTitle(NotebookForm notebookForm);

	public Integer update(NotebookForm notebookForm);

	public void delete(Integer id);

}
