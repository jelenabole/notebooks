package hr.tvz.bole.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.tvz.bole.model.Notebook;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {

	public List<Notebook> findAll();

	public Notebook findById(Integer id);

	// TODO - nepotrebno !?
	public Notebook findByTitle(String title);

	@SuppressWarnings("unchecked")
	public Notebook save(Notebook notebook);

//	public Integer update(Notebook notebook);

	@Query("UPDATE Notebook SET description=:description WHERE id = :id")
	public Integer updateWithoutTitle(@Param("id") Integer id, @Param("description") String description);

	public void deleteById(Integer id);

}
