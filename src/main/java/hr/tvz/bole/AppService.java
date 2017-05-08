package hr.tvz.bole;

import java.util.List;

import org.springframework.stereotype.Service;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

@Service
public class AppService {

	public List<User> findAllUsers() {
		return MockHelper.mockUserList();
	}

	public List<Notebook> findAllNotebooks() {
		return MockHelper.mockNotebookList();
	}

	public User findUserById(String userId) {
		User noteUser = null;
		try {
			Integer id = Integer.parseInt(userId);
			for (User user : MockHelper.mockUserList()) {
				if (user.getId() == id) {
					noteUser = user;
					break;
				}
			}
		} catch (Exception ex) {
			return null;
		}
		return noteUser;
	}

	public User findUserByFullName(String fullName) {
		for (User user : MockHelper.mockUserList()) {
			if (user.getUsername().equals(fullName)) {
				return user;
			}
		}
		return null;
	}

	public Notebook findNotebookById(String notebookId) {
		Notebook noteNotebook = null;
		if (notebookId != null) {
			try {
				Integer id = Integer.parseInt(notebookId);
				for (Notebook notebook : MockHelper.mockNotebookList()) {
					if (notebook.getId().equals(id)) {
						noteNotebook = notebook;
						break;
					}
				}
			} catch (Exception ex) {
				return null;
			}
		}
		return noteNotebook;
	}

}
