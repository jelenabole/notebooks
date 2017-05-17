package hr.tvz.bole.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import hr.tvz.bole.enums.UserRoles;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String surname;
	String username;
	String email;
	String password;
	// TODO - za što služi enabled:
	boolean enabled;

	@ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user"))
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	List<UserRoles> roles;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoles> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "USER - id: " + id + " - fullname: " + name + " " + surname + " - username: "
				+ username + " - password: " + password + " - enabled: " + enabled;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof User))
			return false;

		User user = (User) other;
		System.out.println("USER hash equals - id: " + id.equals(user.id));
		return (id.equals(user.id) && username.equals(user.username));
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + id.hashCode();
		hash = hash * 31 + username.hashCode();

		return hash;
	}

}
