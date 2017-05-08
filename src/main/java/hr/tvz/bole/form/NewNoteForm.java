package hr.tvz.bole.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

public class NewNoteForm {

	// XXX - Thymeleaf vraća empty iz option-a, umjesto null (objekti su null)
	@NotNull
	User user;
	@NotNull
	Notebook notebook;
	@Size(min = 1)
	String header;
	@Size(min = 1)
	String text;
	String important;
	String mark;

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

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
