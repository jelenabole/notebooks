package hr.tvz.bole;

import org.hibernate.validator.constraints.NotEmpty;

public class NewNoteForm {
	
	@NotEmpty(message="Polje ne smije biti prazno")
	String userId;
	@NotEmpty(message="Polje ne smije biti prazno")
	String notebookId;
	@NotEmpty(message="Polje ne smije biti prazno")
	String header;
	@NotEmpty(message="Polje ne smije biti prazno")
	String text;
	
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
