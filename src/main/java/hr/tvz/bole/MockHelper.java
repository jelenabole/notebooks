package hr.tvz.bole;

import java.util.ArrayList;
import java.util.List;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

public class MockHelper {

	public static List<User> mockUserList() {
		List<User> userList = new ArrayList<>();
		userList.add(new User(1, "pperic", "Pero", "Peri�"));
		userList.add(new User(2, "iivic", "Ivo", "Ivi�"));
		userList.add(new User(3, "mmarkic", "Marko", "Marki�"));

		return userList;
	}

	public static List<Notebook> mockNotebookList() {
		List<Notebook> notebookList = new ArrayList<>();
		notebookList.add(new Notebook(1, "Prva bilje�nica", "Opis prve bilje�nice"));
		notebookList.add(new Notebook(2, "Druga bilje�nica", "Opis druge bilje�nice"));
		notebookList.add(new Notebook(3, "Tre�a bilje�nica", "Opis tre�e bilje�nice"));
		notebookList.add(new Notebook(4, "�etvrta bilje�nica", "Opis �etvrte bilje�nice"));

		return notebookList;
	}
}
