package hr.tvz.bole.server.service;

import java.util.List;

import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.model.User;
import hr.tvz.bole.web.form.UserForm;

public interface UserService {

	public List<User> findAll();

	public User findOne(Integer id);

	public User findOneByUsername(String username);

	public User save(User user) throws UserExistsException, RoleExistsForUser;

	public User update(UserForm userForm);

	public void changePassword(Integer id, String password);

	public void delete(Integer id);

	public boolean checkIfUserExists(String username);

}
