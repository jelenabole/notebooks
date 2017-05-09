package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserProjection;
import hr.tvz.bole.web.form.UserForm;

public class UserMapper {

	public static UserForm mapUserToUserForm(User user) {
		UserForm userForm = new UserForm();
		userForm.setId(user.getId());
		userForm.setName(user.getName());
		userForm.setSurname(user.getSurname());
		userForm.setUsername(user.getUsername());
		userForm.setEmail(user.getEmail());

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

		return user;
	}

	public static User mapCurrentUserToUser(UserProjection currentUser) {
		User user = new User();
		user.setId(currentUser.getId());
		user.setUsername(currentUser.getUsername());
		user.setRoles(currentUser.getRoles());

		return user;
	}

	public static UserProjection mapUserToCurrentUser(User user) {
		UserProjection currentUser = new UserProjection();
		currentUser.setId(user.getId());
		currentUser.setUsername(user.getUsername());
		currentUser.setRoles(user.getRoles());

		return currentUser;
	}
}
