package hr.tvz.bole.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String name;
	String surname;
	String username;
	// TODO - nije uvijek potrebno:
	String password;
	// TODO - za što služi enabled:
	boolean enabled;

	public User() {
	};

	public User(String username, String name, String surname) {
		this.username = username;
		this.name = name;
		this.surname = surname;
	}

	// TODO - popraviti u bazi da postoji ID:
	public User(Integer id, String username, String name, String surname) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
	}

	public User(Integer id, String name, String surname, String username, String password, boolean enabled) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "USER - id: " + id + " - fullname: " + name + " " + surname + " - username: " + username
				+ " - password: " + password + " - enabled: " + enabled;
	}
}
