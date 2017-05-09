package hr.tvz.bole.web.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class NotebookForm {

	Integer id;

	@NotBlank
	@Size(max = 50)
	String title;
	@NotBlank
	@Size(max = 100)
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