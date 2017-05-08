package hr.tvz.bole.model;

import java.io.Serializable;

public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	int id;
	String username;
	String role;
	boolean admin;

	public UserRole() {
	};

	public UserRole(String username, boolean admin) {
		this.username = username;
		this.admin = admin;
	};

	public UserRole(int id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role = role;
	}

	public int getId() {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "USER ROLE - id: " + id + " - username: " + username + " - role: " + role + " - admin: " + admin;

	}
}
