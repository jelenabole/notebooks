package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.model.Notebook;

public interface NotebookRepository {

	public List<Notebook> findAll();
	
	public Notebook findOne(Integer id);

	// TODO - nepotrebno !?
	public Notebook findOneByTitle(String title);

	public Integer save(Notebook notebook);

	public Integer update(Notebook notebook);

	public Integer updateWithoutTitle(Notebook notebook);

	public void delete(Integer id);

}
