package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.User;
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
		return new User(userForm.getId(), userForm.getName(), userForm.getSurname(), userForm.getUsername(),
				userForm.getEmail(), userForm.getPassword(), true);
	}
}
