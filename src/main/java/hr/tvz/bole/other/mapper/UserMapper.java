package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.User;
import hr.tvz.bole.web.form.RegisterForm;
import hr.tvz.bole.web.form.UserForm;

public class UserMapper {

	/**** REGISTER FORM ****/

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

	/**** USER FORM ****/

	public static UserForm mapUserToUserForm(User user) {
		UserForm userForm = new UserForm();
		userForm.setId(user.getId());
		userForm.setName(user.getName());
		userForm.setSurname(user.getSurname());
		userForm.setUsername(user.getUsername());
		userForm.setEmail(user.getEmail());
		// userForm.setPassword(user.getPassword());
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

		// TODO - password ne kopirat, kopira se naknadno po potrebi
		// TODO - enabled se ne mijenja
		// user.setPassword(userForm.getPassword());
		user.setEnabled(true);

		// TODO - role kao stringove!
		// TODO - provjeriti na userInfo
		user.setRoles(userForm.getRoles());

		return user;
	}

	/**** CHANGE USER INFO - viewUsers and userInfo ****/

	public static User updateUser(User user, UserForm userForm) {
		// TODO - id should be the same:
		if (!user.getId().equals(userForm.getId())) {
			System.out.println("UserMapper - ERROR - IDs not equal");
		}
		user.setName(userForm.getName());
		user.setSurname(userForm.getSurname());
		user.setEmail(userForm.getEmail());
		// TODO - stare role se brišu, nove dodaju:
		user.setRoles(userForm.getRoles());

		return user;
	}

	public static User changeInfo(User user, UserForm userForm) {
		// TODO - id should be the same:
		if (!user.getId().equals(userForm.getId())) {
			System.out.println("UserMapper - ERROR - IDs not equal");
		}
		user.setName(userForm.getName());
		user.setSurname(userForm.getSurname());
		user.setEmail(userForm.getEmail());

		return user;
	}

	/**** CURRENT USER ****/

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
