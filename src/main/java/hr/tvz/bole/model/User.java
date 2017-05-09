package hr.tvz.bole.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<UserRole> roles;

	public User() {
	};

	public User(Integer id, String username, String name, String surname) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
	}
	
	// findAll:
	public User(Integer id, String username, String name, String surname, String email) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public User(Integer id, String name, String surname, String username, String email, String password,
			boolean enabled) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
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
	
	

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "USER - id: " + id + " - fullname: " + name + " " + surname + " - username: " + username
				+ " - password: " + password + " - enabled: " + enabled;
	}
}
