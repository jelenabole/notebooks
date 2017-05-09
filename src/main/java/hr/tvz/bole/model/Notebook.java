package hr.tvz.bole.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "notebooks")
public class Notebook implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String title;
	String description;
	
	//TODO - skip while importing from database:
	@Transient
	Integer numberOfNotes;

	public Notebook() {
	}

	public Notebook(String title, String description) {
		this.title = title;
		this.description = description;
	}

	// TODO - popraviti u bazi da koristimo ID-eve:
	public Notebook(Integer id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumberOfNotes() {
		return numberOfNotes;
	}

	public void setNumberOfNotes(Integer numberOfNotes) {
		this.numberOfNotes = numberOfNotes;
	}

	@Override
	public String toString() {
		return "NOTEBOOK - id: " + id + " - title: " + title + " - description: " + description;
	}
}
