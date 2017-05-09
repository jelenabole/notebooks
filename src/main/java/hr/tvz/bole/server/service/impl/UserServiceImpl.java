package hr.tvz.bole.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.other.PasswordGenerator;
import hr.tvz.bole.other.mapper.UserMapper;
import hr.tvz.bole.server.repository.UserRepository;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.RoleService;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.UserForm;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	NoteService noteService;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(Integer id) {
		return userRepository.findById(id);
	}

	// TODO - nepotrebno ?!
	public User findOneByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User save(User user) throws UserExistsException, RoleExistsForUser {
		if (checkIfUserExists(user.getUsername())) {
			throw new UserExistsException("user exists: " + user.getUsername());
		}

		user.setPassword(PasswordGenerator.generatePassword(user.getPassword()));
		userRepository.save(user);

		roleService.saveRole(new UserRole(null, user, "ROLE_USER"));

		return user;
	}

	public User update(UserForm userForm) {
		if (userForm.getMatchPassword() != null && !userForm.getMatchPassword().isEmpty()) {
			changePassword(userForm.getId(), userForm.getMatchPassword());
		}

		User user = UserMapper.mapUserFormToUser(userForm);
		// TODO - update
		userRepository.save(user);
		return user;
	}

	public void changePassword(Integer id, String password) {
		String encripted = PasswordGenerator.generatePassword(password);
		userRepository.changePassword(id, encripted);
	}

	public void delete(Integer id) {
		// obrisati role i bilješke korisnika:
		roleService.deleteAllRolesForUser(id);
		noteService.deleteByUser(id);
		userRepository.deleteById(id);
	}

	public boolean checkIfUserExists(String username) {
		if (userRepository.findByUsername(username) == null)
			return false;
		return true;
	}

	@Override
	public void changeEnabledStatus(Integer id) {
		//check current status:
		if (userRepository.findById(id).isEnabled()) {
			userRepository.disableUser(id);
		} else {
			userRepository.enableUser(id);
		}
	}

}
