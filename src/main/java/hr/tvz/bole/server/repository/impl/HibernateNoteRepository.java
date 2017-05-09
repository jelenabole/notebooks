package hr.tvz.bole.server.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.User;
import hr.tvz.bole.server.repository.NoteRepository;

@Repository
@Transactional
public class HibernateNoteRepository implements NoteRepository {

	private SessionFactory sessionFactory;

	@Autowired
	public HibernateNoteRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	static final String SELECT_NOTE = "SELECT n FROM Note n";
	static final String SELECT_BY_USER = "FROM Note WHERE user = :userId";
	static final String DELETE_BY_NOTEBOOK = "DELETE Note WHERE notebook.id = :notebookId";
	static final String DELETE_BY_USER = "DELETE Note WHERE user.id = :userId";

	// TODO - not implemented
	static final String COUNT_NOTES = "SELECT COUNT(*) FROM notes WHERE notebook = ?";

	@Override
	public List<Note> findAll() {
		return currentSession().createQuery(SELECT_NOTE, Note.class).getResultList();
	}

	@Override
	public Note findOne(Integer id) {
		return currentSession().find(Note.class, id);
	}

	@Override
	public List<Note> findByUser(Integer id) {
		User user = new User();
		user.setId(id);
		return currentSession().createQuery(SELECT_BY_USER, Note.class).setParameter("userId", user).getResultList();
	}

	@Override
	public void save(Note note) {
		currentSession().save(note);
	}

	@Override
	public void update(Note note) {
		currentSession().saveOrUpdate(note);
	}

	@Override
	public void delete(Integer id) {
		currentSession().delete((Note) currentSession().load(Note.class, id));
	}

	@Override
	public void deleteByNotebook(Integer notebookId) {
		currentSession().createQuery(DELETE_BY_NOTEBOOK).setParameter("notebookId", notebookId).executeUpdate();
	}

	@Override
	public void deleteByUser(Integer userId) {
		currentSession().createQuery(DELETE_BY_USER).setParameter("userId", userId).executeUpdate();
	}

	@Override
	public Integer getNumberOfNotes(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getNumberOfNotesForUser(String title, String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
