package hr.tvz.bole.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hr.tvz.bole.web.form.NewNoteForm;

@Entity
@Table(name = "notes")
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@ManyToOne
	@JoinColumn(name = "user")
	User user;
	@ManyToOne
	@JoinColumn(name = "notebook")
	Notebook notebook;

	String header;
	String text;
	Boolean important;
	String mark;
	Boolean status;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Note() {
	}

	public Note(Integer id, User user, Notebook notebook, String header, String text, Boolean important, String mark) {
		this.id = id;
		this.user = user;
		this.notebook = notebook;
		this.header = header;
		this.text = text;
		this.important = important;
		this.mark = mark;
	}

	// XXX - prebacit u mapper:
	// TODO - mapper nije potreban, zbog mapera u bazi:
	public Note(NewNoteForm newNoteForm) {
		this.id = newNoteForm.getId();
		this.user = newNoteForm.getUser();
		this.notebook = newNoteForm.getNotebook();
		this.header = newNoteForm.getHeader();
		this.text = newNoteForm.getText();
		this.important = newNoteForm.getImportant() != null;
		this.mark = newNoteForm.getMark();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Override
	public String toString() {
		return "NOTE - id: " + id + " - header: " + header + " - text: " + text + " - important: " + important
				+ " - mark: " + mark + "\n\t" + user.toString() + "\n\t" + notebook.toString();
	}
}
