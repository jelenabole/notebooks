package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.User;
import hr.tvz.bole.web.form.RegisterForm;
import hr.tvz.bole.web.form.UserForm;

public class UserMapper {

	public static RegisterForm mapUserToRegisterForm(User user) {
		RegisterForm registerForm = new RegisterForm();
		registerForm.setId(user.getId());
		registerForm.setName(user.getName());
		registerForm.setSurname(user.getSurname());
		registerForm.setUsername(user.getUsername());
		registerForm.setEmail(user.getEmail());

		return registerForm;
	}

	public static User mapRegisterFormToUser(RegisterForm registerForm) {
		User user = new User();
		user.setId(registerForm.getId());
		user.setName(registerForm.getName());
		user.setSurname(registerForm.getSurname());
		user.setUsername(registerForm.getUsername());
		user.setEmail(registerForm.getEmail());

		user.setPassword(registerForm.getPassword());
		user.setEnabled(true);

		return user;
	}

	public static UserForm mapUserToUserForm(User user) {
		UserForm userForm = new UserForm();
		userForm.setId(user.getId());
		userForm.setName(user.getName());
		userForm.setSurname(user.getSurname());
		userForm.setUsername(user.getUsername());
		userForm.setEmail(user.getEmail());
		userForm.setPassword(user.getPassword());
		userForm.setRoles(user.getRoles());

		return userForm;
	}

	public static User mapUserFormToUser(UserForm userForm) {
		User user = new User();
		user.setId(userForm.getId());
		user.setName(userForm.getName());
		user.setSurname(userForm.getSurname());
		user.setUsername(userForm.getUsername());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setEnabled(true);

		// TODO - role kao stringove!
		// TODO - provjeriti na userInfo
		user.setRoles(userForm.getRoles());

		return user;
	}

	public static User mapCurrentUserToUser(CurrentUser currentUser) {
		User user = new User();
		user.setId(currentUser.getId());
		user.setUsername(currentUser.getUsername());

		// TODO - role kao stringove ?!
		user.setRoles(currentUser.getRoles());

		return user;
	}

	public static CurrentUser mapUserToCurrentUser(User user) {
		CurrentUser currentUser = new CurrentUser();
		currentUser.setId(user.getId());
		currentUser.setUsername(user.getUsername());
		currentUser.setRoles(user.getRoles());

		return currentUser;
	}
}
