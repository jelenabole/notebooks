package hr.tvz.bole.server.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.server.repository.NotebookRepository;

@Repository
@Transactional
public class HibernateNotebookRepository implements NotebookRepository {

	private SessionFactory sessionFactory;

	@Autowired
	public HibernateNotebookRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	static final String SELECT_NOTEBOOKS = "SELECT * FROM notebooks";
	static final String UPDATE_NOTEBOOK = "UPDATE notebooks SET title=?, description=? WHERE id = ?";
	static final String DELETE_NOTEBOOK = "DELETE FROM notebooks WHERE id = ?";
	static final String UPDATE_NOTEBOOK_WITHOUT_TITLE = "UPDATE notebooks SET description=? WHERE id = ?";

	@Override
	public List<Notebook> findAll() {
		return currentSession().createQuery("SELECT b FROM Notebook b", Notebook.class).getResultList();
	}

	@Override
	public Notebook findOne(Integer id) {
		return currentSession().find(Notebook.class, id);
	}

	@Override
	public Notebook findOneByTitle(String title) {
		// currentSession().findByNamedParam("FROM notebooks WHERE title =
		// :title", "title", (Object) title).list();
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(Notebook.class); // criteria.add(example);
		 * //proširivanje novim kriterijima za dohvat
		 * criteria.createCriteria("title").add(
		 * org.hibernate.criterion.Restrictions.eq("title", title));
		 * 
		 * return currentSession().cretreateCriteria(criteria);
		 */
		return (Notebook) currentSession().createQuery("FROM notebooks WHERE title = :title").list().get(0);
	}

	@Override
	public Integer save(Notebook notebook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Notebook notebook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateWithoutTitle(Notebook notebook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	// TODO - provjeriti ostale metode:
	/*
	 * @Override public Integer save(Notebook notebook) { SimpleJdbcInsert
	 * jdbcInsert = new
	 * SimpleJdbcInsert(jdbcTemplate).withTableName("notebooks");
	 * jdbcInsert.setGeneratedKeyName("id");
	 * 
	 * Map<String, Object> args = new HashMap<String, Object>();
	 * args.put("title", notebook.getTitle()); args.put("description",
	 * notebook.getDescription());
	 * 
	 * return jdbcInsert.executeAndReturnKey(args).intValue(); }
	 * 
	 * @Override public Integer update(Notebook notebook) { return
	 * jdbcTemplate.update(UPDATE_NOTEBOOK, notebook.getTitle(),
	 * notebook.getDescription(), notebook.getId()); }
	 * 
	 * @Override public Integer updateWithoutTitle(Notebook notebook) { return
	 * jdbcTemplate.update(UPDATE_NOTEBOOK_WITHOUT_TITLE,
	 * notebook.getDescription(), notebook.getId()); }
	 * 
	 * @Override public void delete(Integer id) {
	 * jdbcTemplate.update(DELETE_NOTEBOOK, id); }
	 * 
	 * private static final class NotebookRowMapper implements
	 * RowMapper<Notebook> { public Notebook mapRow(ResultSet rs, int rowNum)
	 * throws SQLException { int id = rs.getInt("id"); String title =
	 * rs.getString("title"); String description = rs.getString("description");
	 * return new Notebook(id, title, description); } }
	 */
}
