package hr.tvz.bole.model;

import java.io.Serializable;

public class Notebook implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String title;
	String description;

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

	@Override
	public String toString() {
		return "NOTEBOOK - id: " + id + " - title: " + title + " - description: " + description;
	}
}
