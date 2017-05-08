package hr.tvz.bole;

import java.util.ArrayList;
import java.util.List;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

public class MockHelper {

	public static List<User> mockUserList() {
		List<User> userList = new ArrayList<>();
		userList.add(new User(1, "pperic", "Pero", "Periæ"));
		userList.add(new User(2, "iivic", "Ivo", "Iviæ"));
		userList.add(new User(3, "mmarkic", "Marko", "Markiæ"));

		return userList;
	}

	public static List<Notebook> mockNotebookList() {
		List<Notebook> notebookList = new ArrayList<>();
		notebookList.add(new Notebook(1, "Prva bilježnica", "Opis prve bilježnice"));
		notebookList.add(new Notebook(2, "Druga bilježnica", "Opis druge bilježnice"));
		notebookList.add(new Notebook(3, "Treæa bilježnica", "Opis treæe bilježnice"));
		notebookList.add(new Notebook(4, "Èetvrta bilježnica", "Opis èetvrte bilježnice"));

		return notebookList;
	}
}
