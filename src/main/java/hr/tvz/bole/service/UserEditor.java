package hr.tvz.bole.service;

import java.beans.PropertyEditorSupport;

import hr.tvz.bole.MockHelper;
import hr.tvz.bole.model.User;

public class UserEditor extends PropertyEditorSupport {

	//TODO - ne smije biti appService nego MockHelper ?!?
	//appService ne radi ?
	//XXX - pokreće se kada se stvara <select> lista
	@Override
	public void setAsText(String text) {
		User currentUser = null;
		for (User user : MockHelper.mockUserList()) {
			if (user.getId().equals(Integer.parseInt(text))) {
				currentUser = user;
				break;
			}
		}
		this.setValue(currentUser);
	}

	@Override
	public String getAsText() {
		User user = (User) this.getValue();
		return (user != null ? user.getId().toString() : "");
	}
}
