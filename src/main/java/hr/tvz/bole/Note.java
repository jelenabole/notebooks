package hr.tvz.bole;

import org.springframework.stereotype.Component;

@Component
public class Note {
	User user;
	Notebook notebook;
	String header;
	String text;
	
	public Note() {}
	
	public Note(User user, Notebook notebook, String header, String text) {
		this.user = user;
		this.notebook = notebook;
		this.header = header;
		this.text = text;
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
}
