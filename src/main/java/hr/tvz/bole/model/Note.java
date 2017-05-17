package hr.tvz.bole.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import hr.tvz.bole.enums.DBStatus;
import hr.tvz.bole.enums.NoteImportance;
import hr.tvz.bole.enums.NoteMark;

@Entity
@Table(name = "notes")
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrePersist
	@PreUpdate
	public void prePersist() {
		if (importance == null)
			importance = NoteImportance.NOT_IMPORTANT;
		if (status == null)
			status = DBStatus.ACTIVE;
	}

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

	@Enumerated(EnumType.STRING)
	NoteImportance importance;
	@Enumerated(EnumType.STRING)
	NoteMark mark;
	@Enumerated(EnumType.STRING)
	DBStatus status;

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

	public NoteImportance getImportance() {
		return importance;
	}

	public void setImportance(NoteImportance importance) {
		this.importance = importance;
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

	@Override
	public String toString() {
		String string = "NOTE - id: " + id + " - header: " + header + " - text: " + text
				+ " - important: " + importance.name();

		if (mark != null)
			string += " - mark: " + mark.name();

		string += " - status: " + status.name() + "\n\t" + user.toString() + "\n\t"
				+ notebook.toString();

		return string;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Note))
			return false;

		Note note = (Note) other;
		System.out.println("NOTE hash equals - id: " + id.equals(note.id));
		return (id.equals(note.id) && header.equals(note.header));
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + id.hashCode();
		hash = hash * 31 + header.hashCode();

		return hash;
	}
}
