package hr.tvz.bole.service;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.bole.model.User;
import hr.tvz.bole.repository.UserRepository;

@Component
public class UserEditor extends PropertyEditorSupport {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void setAsText(String text) {
		User currentUser = null;
		for (User user : userRepository.findAll()) {
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
