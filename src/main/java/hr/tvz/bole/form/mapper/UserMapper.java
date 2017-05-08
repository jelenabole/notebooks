package hr.tvz.bole.form.mapper;

import hr.tvz.bole.form.UserForm;
import hr.tvz.bole.model.User;

public class UserMapper {

	public static UserForm mapUserToUserForm(User user) {
		UserForm userForm = new UserForm();
		userForm.setName(user.getName());
		userForm.setSurname(user.getSurname());
		userForm.setEmail(user.getEmail());
		userForm.setPassword(user.getPassword());

		return userForm;
	}

	public static User mapUserFormToUser(UserForm userForm) {
		return new User(null, userForm.getName(), userForm.getSurname(), userForm.getUsername(), userForm.getEmail(),
				userForm.getPassword(), true);
	}
}
