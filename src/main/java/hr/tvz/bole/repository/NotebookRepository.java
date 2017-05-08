package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.model.Notebook;

public interface NotebookRepository {

	public List<Notebook> findAll();

	public Notebook findOne(String title);
	
}
