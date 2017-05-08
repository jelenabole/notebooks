package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.model.User;

public interface UserRepository {

	public List<User> findAll();

	public User findOne(String username);

	public boolean hasAdminRole(String username);

	public User save(User user) throws UserExistsException;

	// add methods - add user roles, get user roles... change roles,...
}
