package hr.tvz.bole.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@ManyToOne
	@JoinColumn(name = "user")
	User user;
	String role;

	public UserRole() {
	};

	public UserRole(Integer id, User user, String role) {
		this.id = id;
		this.user = user;
		this.role = role;
	}

	// stvaranje UserRole za session:
	public UserRole(User user, boolean hasAdminRole) {
		this.user = user;
		if (hasAdminRole)
			role = "ROLE_ADMIN";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "USER ROLE - id: " + id + " - userId: " + user + " - role: " + role;

	}
}
