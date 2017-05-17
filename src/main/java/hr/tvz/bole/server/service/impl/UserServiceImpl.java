package hr.tvz.bole.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.enums.UserRoles;
import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.other.PasswordGenerator;
import hr.tvz.bole.other.mapper.UserMapper;
import hr.tvz.bole.server.repository.UserRepository;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.RoleService;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.UserForm;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
			throw new UserExistsException("username exists: " + user.getUsername());
		}

		user.setPassword(PasswordGenerator.generatePassword(user.getPassword()));
		userRepository.save(user);

		roleService.saveRole(new UserRole(null, user, "ROLE_USER"));

		return user;
	}

	public User update(UserForm userForm) {
		if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
			// changePassword(userForm.getId(), userForm.getPassword());
			System.out.println("set new password: " + userForm.getPassword());
			userForm.setPassword(PasswordGenerator.generatePassword(userForm.getNewPassword()));
		}

		// XXX - obavezno barem jedna rola (user):s
		if (userForm.getRoles() == null || userForm.getRoles().isEmpty()) {
			logger.info("UPDATE - no roles - added 'USER'");
			userForm.setRoles(new ArrayList<>());
			userForm.getRoles().add(UserRoles.ROLE_USER);
		}

		User user = UserMapper.mapUserFormToUser(userForm);
		userRepository.save(user);

		return user;
	}

	// TODO - nepotrebno ??
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
		// check current status:
		if (userRepository.findById(id).isEnabled()) {
			userRepository.disableUser(id);
		} else {
			userRepository.enableUser(id);
		}
	}

	@Override
	public CurrentUser getCurrentUser(String username) {
		return UserMapper.mapUserToCurrentUser(userRepository.findByUsername(username));
	}

	@Override
	public List<User> getFilteredUsers(FilterForm filterForm) {
		logger.info("order by: " + filterForm.getOrderBy() + " - " + filterForm.getOrderDirection()
				+ " (" + filterForm.getSearchBy() + ")");

		if (!filterForm.getSearchBy().isEmpty()) {
			return userRepository
					.findAllByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
							filterForm.getSearchBy(), filterForm.getSearchBy(),
							filterForm.getSearchBy(), filterForm.getSearchBy());
		}

		List<User> users = getSortedUsers(filterForm);
		return users;
	}

	private List<User> getSortedUsers(FilterForm filterForm) {
		switch (filterForm.getOrderDirection()) {
			case "asc":
				return getAscending(filterForm.getOrderBy());
			case "desc":
				return getDescending(filterForm.getOrderBy());
		}
		logger.error("notebook filter - return NULL");
		return null;
	}

	private List<User> getAscending(String orderBy) {
		switch (orderBy) {
			case "name":
				return userRepository.findAllByOrderByNameAsc();
			case "surname":
				return userRepository.findAllByOrderBySurnameAsc();
			case "username":
				return userRepository.findAllByOrderByUsernameAsc();
			case "email":
				return userRepository.findAllByOrderByEmailAsc();
			case "enabled":
				return userRepository.findAllByOrderByEnabledAsc();
			case "role":
			default:
				return userRepository.findAll();
		}
	}

	private List<User> getDescending(String orderBy) {
		switch (orderBy) {
			case "name":
				return userRepository.findAllByOrderByNameDesc();
			case "surname":
				return userRepository.findAllByOrderBySurnameDesc();
			case "username":
				return userRepository.findAllByOrderByUsernameDesc();
			case "email":
				return userRepository.findAllByOrderByEmailDesc();
			case "enabled":
				return userRepository.findAllByOrderByEnabledDesc();
			case "role":
			default:
				return userRepository.findAllByOrderByIdDesc();
		}
	}

}
