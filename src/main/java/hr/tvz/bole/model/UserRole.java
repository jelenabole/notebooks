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
		return role.equals("ROLE_ADMIN");
	}

	@Override
	public String toString() {
		return "USER ROLE - id: " + id + " - userId: " + user.getId() + " - role: " + role;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof UserRole))
			return false;

		UserRole role = (UserRole) other;
		System.out.println("ROLE hash equals - id: " + id.equals(role.id));
		return (id.equals(role.id) && user.getId().equals(role.getUser().getId())
				&& role.equals(role.role));
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + id.hashCode();
		hash = hash * 31 + user.getId().hashCode();
		hash = hash * 31 + role.hashCode();

		return hash;
	}
}
