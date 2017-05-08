package hr.tvz.bole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewNoteForm {
	
	@NotNull
	String userId;
	@NotNull
	String notebookId;
	@Size(min=1)
	String header;
	@Size(min=1)
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
