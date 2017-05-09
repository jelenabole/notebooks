package hr.tvz.bole.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.bole.model.User;
import hr.tvz.bole.server.service.UserService;

@Component
public class UserEditor extends PropertyEditorSupport {

	@Autowired
	UserService userService;

	@Override
	public void setAsText(String text) {
		User currentUser = null;
		for (User user : userService.findAll()) {
			if (user.getId() == Integer.parseInt(text)) {
				currentUser = user;
				break;
			}
		}
		this.setValue(currentUser);
	}

	@Override
	public String getAsText() {
		User user = (User) this.getValue();
		return (user != null ? String.valueOf(user.getId()) : "");
	}
}
