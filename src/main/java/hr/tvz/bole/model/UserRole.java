package hr.tvz.bole.model;

import java.io.Serializable;

public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String username;
	String role;

	public UserRole() {
	};

	public UserRole(Integer id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role = role;
	}
	
	//stvaranje UserRole za session:
	public UserRole(String username, boolean hasAdminRole) {
		this.username = username;
		if (hasAdminRole)
			role = "ROLE_ADMIN";
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return role == "ROLE_ADMIN";
	}

	@Override
	public String toString() {
		return "USER ROLE - id: " + id + " - username: " + username + " - role: " + role;

	}
}
