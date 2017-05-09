package hr.tvz.bole.web.form;

import javax.validation.constraints.Size;

public class NotebookForm {

	Integer id;

	@Size(min = 1)
	String title;
	@Size(min = 1)
	String description;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
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

	public void setId(Integer id) {
		this.id = id;
	}
}