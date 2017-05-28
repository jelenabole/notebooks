package hr.tvz.bole.server.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	// XXX - update - view users (admin changes):
	public User update(UserForm userForm) {
		User user = findOne(userForm.getId());
		user = UserMapper.updateUser(user, userForm);

		// XXX - obavezno barem jedna rola (user):
		if (user.getRoles() == null || user.getRoles().isEmpty()) {
			logger.info("UPDATE - no roles - added 'USER'");
			user.setRoles(Arrays.asList(UserRoles.ROLE_USER));
		}

		userRepository.save(user);

		return user;
	}

	// XXX - update - user info (user changes):
	public User changeInfo(UserForm userForm) {
		User user = findOne(userForm.getId());
		user = UserMapper.changeInfo(user, userForm);

		// change pass if necessary:
		if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
			user.setPassword(PasswordGenerator.generatePassword(userForm.getNewPassword()));
		}

		userRepository.save(user);

		return user;
	}

	public void delete(Integer id) {
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

		List<User> users = findAll();

		// orderBy == nul samo na onload (vrati sve):
		if (filterForm.getOrderBy() == null) {
			return users;
		}

		// return by expression:
		if (!filterForm.getSearchBy().isEmpty()) {
			users = filter(users, filterForm.getSearchBy().toLowerCase());
		}

		// sort by:
		if (!filterForm.getOrderBy().isEmpty()) {
			users = orderBy(users, filterForm.getOrderBy());
		}

		// reverse (if desc):
		if (filterForm.getOrderDirection().equals("desc")) {
			users = reverseOrder(users);
		}

		return users;
	}

	// filter - filter by:
	private List<User> filter(List<User> users, String search) {
		return users.stream()
				.filter(e -> (e.getName().toLowerCase().contains(search)
						|| e.getSurname().toLowerCase().contains(search)
						|| e.getUsername().toLowerCase().contains(search)
						|| e.getEmail().toLowerCase().contains(search)))
				.collect(Collectors.toList());
	}

	// filter - order by:
	private List<User> orderBy(List<User> users, String orderBy) {
		switch (orderBy) {
			case "name":
				users = users.stream()
						.sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
						.collect(Collectors.toList());
				break;
			case "surname":
				users = users.stream()
						.sorted((e1, e2) -> e1.getSurname().compareToIgnoreCase(e2.getSurname()))
						.collect(Collectors.toList());
				break;
			case "username":
				users = users.stream()
						.sorted((e1, e2) -> e1.getUsername().compareToIgnoreCase(e2.getUsername()))
						.collect(Collectors.toList());
				break;
			case "email":
				users = users.stream()
						.sorted((e1, e2) -> e1.getEmail().compareToIgnoreCase(e2.getEmail()))
						.collect(Collectors.toList());
				break;
			case "enabled":
				users = users.stream()
						.sorted((e1, e2) -> String.valueOf(e1.isEnabled())
								.compareTo(String.valueOf(e2.isEnabled())))
						.collect(Collectors.toList());
				break;
			case "role":
				// TODO - roles (with locale)
				// po jednoj roli - uzmemo role i redoslijed iz enuma
				// ako postoji rola sa order() 0, 1, 2, ...
				break;
		}
		return users;
	}

	// filter - reverse order:
	private List<User> reverseOrder(List<User> users) {
		int size = users.size() / 2;
		int k = users.size() - 1;
		for (int i = 0; i < size; i++) {
			User temp = users.get(i);
			users.set(i, users.get(k));
			users.set(k, temp);
			k--;
		}
		return users;
	}

}
