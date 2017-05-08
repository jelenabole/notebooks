package hr.tvz.bole.model;

import hr.tvz.bole.form.NewNoteForm;

public class Note {
	User user;
	Notebook notebook;
	String header;
	String text;
	Boolean important;
	String mark;

	public Note() {
	}

	// XXX - prebacit u mapper:
	public Note(NewNoteForm newNoteForm) {
		this.user = newNoteForm.getUser();
		this.notebook = newNoteForm.getNotebook();
		this.header = newNoteForm.getHeader();
		this.text = newNoteForm.getText();
		this.important = newNoteForm.getImportant() != null;
		this.mark = newNoteForm.getMark();
	}

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

	public Boolean getImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
