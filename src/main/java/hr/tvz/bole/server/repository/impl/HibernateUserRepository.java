package hr.tvz.bole.server.repository.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.model.User;
import hr.tvz.bole.server.repository.UserRepository;

@Repository
@Transactional
public class HibernateUserRepository implements UserRepository {

	private SessionFactory sessionFactory;

	@Autowired
	public HibernateUserRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	static final String SELECT_USERS = "SELECT n FROM Note n";
	static final String SELECT_WITHOUT_PASS = "SELECT new hr.tvz.bole.model.User(n.id, n.name, n.surname, n.username, n.email) FROM User n";
	static final String SELECT_BY_USERNAME = "FROM User WHERE username = :username";
	static final String UPDATE_USER = "UPDATE User SET name=:name, surname=:surname, email=:email WHERE id=:id";
	static final String UPDATE_PASS = "UPDATE User SET password=:password WHERE id=:id";

	@Override
	public List<User> findAll() {
		return currentSession().createQuery(SELECT_WITHOUT_PASS, User.class).getResultList();
	}

	@Override
	public User findOne(Integer id) {
		// TODO - provjeriti da li radi
		return currentSession().find(User.class, id);
	}

	@Override
	public User findOneByUsername(String username) {
		try {
			return currentSession().createQuery(SELECT_BY_USERNAME, User.class).setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public void save(User user) {
		currentSession().saveOrUpdate(user);
	}

	@Override
	public void update(User user) {
		currentSession().createQuery(UPDATE_USER).setParameter("name", user.getName())
				.setParameter("surname", user.getSurname()).setParameter("email", user.getEmail())
				.setParameter("id", user.getId()).executeUpdate();
	}

	@Override
	public void changePassword(Integer id, String password) {
		currentSession().createQuery(UPDATE_PASS).setParameter("password", password).setParameter("id", id)
				.executeUpdate();
	}

	@Override
	public void delete(Integer id) {
		// currentSession().delete(user);

		currentSession().delete((User) currentSession().load(User.class, id));
		// This makes the pending delete to be done
		// currentSession().flush() ;
	}

}
