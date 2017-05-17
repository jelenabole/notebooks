package hr.tvz.bole.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.bole.model.Notebook;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {

	public List<Notebook> findAll();

	public Notebook findById(Integer id);

	@SuppressWarnings("unchecked")
	public Notebook save(Notebook notebook);

	public void deleteById(Integer id);

	// XXX -sortiranje:
	public List<Notebook> findAllByOrderByTitleAsc();

	public List<Notebook> findAllByOrderByDescriptionAsc();

	public List<Notebook> findAllByOrderByIdDesc();

	public List<Notebook> findAllByOrderByTitleDesc();

	public List<Notebook> findAllByOrderByDescriptionDesc();
	
	public List<Notebook> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String str1, String str2);
	public List<Notebook> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByIdDesc(String str1, String str2);


}
