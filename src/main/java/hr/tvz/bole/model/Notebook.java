package hr.tvz.bole.model;

public class Notebook {
	Integer id;
	String title;
	String description;
	
	public Notebook () {}
	
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
}