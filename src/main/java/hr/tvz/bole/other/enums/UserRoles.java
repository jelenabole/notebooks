package hr.tvz.bole.other.enums;

public enum UserRoles {
	ADMIN(true, "ROLE_ADMIN", "role.admin"), USER(false, "ROLE_USER", "role.admin");

	private boolean adminRights;
	private String name;
	private String translation;

	UserRoles(boolean adminRights, String name, String translation) {
		this.adminRights = adminRights;
		this.name = name;
		this.translation = translation;
	}

	public boolean isAdminRights() {
		return adminRights;
	}

	public void setAdminRights(boolean adminRights) {
		this.adminRights = adminRights;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}
}
