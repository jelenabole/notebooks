package hr.tvz.bole.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.bole.PasswordGenerator;
import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.form.UserForm;
import hr.tvz.bole.form.mapper.UserMapper;
import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.repository.NoteRepository;
import hr.tvz.bole.repository.RoleRepository;
import hr.tvz.bole.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository userRoleRepository;
	@Autowired
	NoteRepository noteRepository;

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}
	
	public User findOneByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}
	
	public User findInfoByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}

	public User saveUser(User user) throws UserExistsException, RoleExistsForUser {
		if (checkIfUserExists(user.getUsername())) {
			throw new UserExistsException("user exists: " + user.getUsername());
		}

		user.setPassword(PasswordGenerator.generatePassword(user.getPassword()));
		user.setId(userRepository.save(user));

		userRoleRepository.save(new UserRole(null, user.getUsername(), "ROLE_USER"));

		return user;
	}

	public Integer updateUser(UserForm userForm) {
		//promijeniti pass po potrebi:
		if (userForm.getMatchPassword() != null && !userForm.getMatchPassword().isEmpty()) {
			changePassword(userForm.getId(), userForm.getMatchPassword());
		}
		
		return userRepository.update(UserMapper.mapUserFormToUser(userForm));
	}

	public Integer changePassword(Integer id, String password) {
		String encripted = PasswordGenerator.generatePassword(password);
		return userRepository.changePassword(id, encripted);
	}

	public void deleteUser(Integer id) {
		String username = findOne(id).getUsername();
		// XXX - obrisati role korisnika:
		userRoleRepository.deleteAllByUsername(username);
		//TODO - paziti da ne postoje bilješke vezane za korisnika
		noteRepository.deleteByUsername(username);
		userRepository.delete(id);
	}

	public boolean checkIfUserExists(String username) {
		if (userRepository.findOneByUsername(username) == null) {
			return false;
		}
		return true;
	}

	/**** ROLES *******/

	public List<UserRole> findAllRoles() {
		return userRoleRepository.findAll();
	}

	public List<UserRole> findAllRolesForUser(String username) {
		return userRoleRepository.findAllByUsername(username);
	}

	public UserRole findOneRoleForUser(String username, String role) {
		return userRoleRepository.findOne(username, role);
	}

	public UserRole saveRole(UserRole role) throws RoleExistsForUser {
		if (!hasRole(role.getUsername(), role.getRole())) {
			throw new RoleExistsForUser("Role " + role.getRole() + " exists for the user " + role.getUsername());
		}

		role.setId(userRoleRepository.save(role));
		return role;
	}

	Integer updateRole(UserRole userRole) {
		return userRoleRepository.update(userRole);
	}

	//TODO - kod brisanja paziti da korisnik ima barem jednu rolu ?!
	void deleteRole(UserRole role) {
		userRoleRepository.delete(role.getId());
	}

	void deleteAllRolesForUser(User user) {
		userRoleRepository.deleteAllByUsername(user.getUsername());
	}

	public boolean hasRole(String username, String userRole) {
		if (userRoleRepository.findOne(username, userRole) == null)
			return false;
		return true;
	}

	public boolean hasAdminRole(String username) {
		return hasRole(username, "ROLE_ADMIN");
	}

}
