package hr.tvz.bole.form;

import javax.validation.constraints.Size;

import hr.tvz.bole.validators.PasswordMatch;
import hr.tvz.bole.validators.ValidEmail;

@PasswordMatch
public class UserForm {

	Integer id;

	@Size(min = 1)
	String name;
	@Size(min = 1)
	String surname;
	@Size(min = 1)
	String username;
	@Size(min = 1)
	private String password;
	private String matchPassword;
	@Size(min = 1)
	@ValidEmail
	private String email;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchPassword() {
		return matchPassword;
	}

	public void setMatchPassword(String matchPassword) {
		this.matchPassword = matchPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}