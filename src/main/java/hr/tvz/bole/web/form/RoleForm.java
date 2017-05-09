package hr.tvz.bole.web.form;

import javax.validation.constraints.NotNull;

import hr.tvz.bole.web.validators.PasswordMatch;

@PasswordMatch
public class RoleForm {

	Integer id;

	@NotNull
	Integer userId;
	@NotNull
	String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}