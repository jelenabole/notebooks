package hr.tvz.bole.web.form;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import hr.tvz.bole.enums.UserRoles;
import hr.tvz.bole.web.validators.ValidEmail;

public class UserForm {

	Integer id;

	@NotBlank
	@Size(max = 50)
	String name;
	@NotBlank
	@Size(max = 50)
	String surname;
	@NotBlank
	@Size(max = 50)
	String username;

	@ValidEmail
	@NotBlank
	@Size(max = 50)
	String email;

	String newPassword;

	List<UserRoles> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoles> roles) {
		this.roles = roles;
	}

}