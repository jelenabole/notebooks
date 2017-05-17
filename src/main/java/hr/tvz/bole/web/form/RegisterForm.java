package hr.tvz.bole.web.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import hr.tvz.bole.web.validators.PasswordMatch;
import hr.tvz.bole.web.validators.ValidEmail;

@PasswordMatch
public class RegisterForm {

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
	private String email;

	@NotBlank
	@Size(max = 50)
	private String password;
	private String matchPassword;

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