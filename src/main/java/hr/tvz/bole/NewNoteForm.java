package hr.tvz.bole;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class NewNoteForm {

	// TODO - Thymeleaf vra�a empty na option, umjesto null:
	@NotEmpty
	String userId;
	@NotEmpty
	String notebookId;
	@Size(min = 1)
	String header;
	@Size(min = 1)
	String text;

	String importance;

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}

	public String getUserId() {
		return userId;
	}

	public String getNotebookId() {
		return notebookId;
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
}
