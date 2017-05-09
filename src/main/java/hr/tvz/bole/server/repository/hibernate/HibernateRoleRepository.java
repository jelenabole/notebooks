package hr.tvz.bole.server.repository.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hr.tvz.bole.model.UserRole;

public class HibernateRoleRepository {

	private SessionFactory sessionFactory;

	@Autowired
	public HibernateRoleRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	static final String SELECT_ALL = "SELECT r FROM UserRole r";
	static final String SELECT_BY_USER = "FROM User WHERE user = :userId";
	static final String SELECT_ONE = "FROM UserRole r WHERE r.user = :user AND r.role = :role";
	static final String DELETE_BY_USER = "DELETE UserRole WHERE user = :userId";

	public List<UserRole> findAll() {
		return currentSession().createQuery(SELECT_ALL, UserRole.class).getResultList();
	}

	public UserRole findOne(Integer userId, String role) {
//		try {
//			return (UserRole) currentSession().createQuery(SELECT_ONE).setProperties(new UserRole(null, userId, role))
//					.getSingleResult();
//		} catch (NoResultException ex) {
			return null;
//		}
	}

	public List<UserRole> findAllByUser(Integer userId) {
		// TODO - provjeriti, negdje nije radilo - zbog reference na objekt
		try {
			return currentSession().createQuery(SELECT_BY_USER, UserRole.class).setParameter("userId", userId)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public void save(UserRole userRole) {
		currentSession().saveOrUpdate(userRole);
	}

	public void delete(Integer id) {
		currentSession().delete((UserRole) currentSession().load(UserRole.class, id));
	}

	public void deleteAllByUserId(Integer userId) {
		currentSession().createQuery(DELETE_BY_USER).setParameter("userId", userId).executeUpdate();
	}
}
