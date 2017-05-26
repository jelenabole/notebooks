package hr.tvz.bole.web.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class AdminNoteForm {

	// XXX - Thymeleaf vraca empty iz option-a, umjesto null (objekti su null)
	Integer id;

	//umjesto notNull - id, a ne objekt
	@Min(value=1, message = "msg1")
	Integer user;
	@Min(value=1, message = "msg1")
	Integer notebook;
	@NotBlank
	@Size(max = 100)
	String header;
	@NotBlank
	@Size(max = 1000)
	String text;

	String important;
	String mark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getNotebook() {
		return notebook;
	}

	public void setNotebook(Integer notebook) {
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

	@Override
	public String toString() {
		String string = "NOTE - id: " + id + " - header: " + header + " - text: " + text;

		if (important != null)
			string += " - important: " + important;

		if (mark != null)
			string += " - mark: " + mark;

		string += "\n\t" + user + "\n\t" + notebook;

		return string;
	}
}
