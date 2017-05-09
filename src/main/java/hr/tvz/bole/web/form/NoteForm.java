package hr.tvz.bole.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import hr.tvz.bole.enums.DBStatus;
import hr.tvz.bole.enums.NoteImportance;
import hr.tvz.bole.enums.NoteMark;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

public class NoteForm {

	// XXX - Thymeleaf vraca empty iz option-a, umjesto null (objekti su null)
	Integer id;

	@NotNull
	User user;
	@NotNull
	Notebook notebook;
	@NotBlank
	@Size(max = 100)
	String header;
	@NotBlank
	@Size(max = 1000)
	String text;

	NoteImportance important;
	NoteMark mark;
	DBStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public NoteImportance getImportant() {
		return important;
	}

	public void setImportant(NoteImportance important) {
		this.important = important;
	}

	public NoteMark getMark() {
		return mark;
	}

	public void setMark(NoteMark mark) {
		this.mark = mark;
	}

	public DBStatus getStatus() {
		return status;
	}

	public void setStatus(DBStatus status) {
		this.status = status;
	}

}
