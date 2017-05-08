package hr.tvz.bole.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

public class NewNoteForm {

	// TODO - Thymeleaf vraća empty iz option, umjesto null:
	@NotNull
	User user;

	@NotNull
	Notebook notebook;

	@Size(min = 1)
	String header;
	@Size(min = 1)
	String text;
	String klasa;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Notebook getNotebook() {
		return notebook;
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	public String getKlasa() {
		return klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}

	String importance;

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
