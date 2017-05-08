package hr.tvz.bole.model;

public class User {
	Integer id;
	String username;
	String name;
	String surname;

	public User() {};

	public User(int id, String username, String name, String surname) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
