package hr.tvz.bole.enums;

public enum UserRoles {
	ROLE_ADMIN(true, "role.admin"),
	ROLE_USER(false, "role.user");

	private boolean adminRights;
	private String translation;

	UserRoles(boolean adminRights, String translation) {
		this.adminRights = adminRights;
		this.translation = translation;
	}

	public boolean isAdminRights() {
		return adminRights;
	}

	public void setAdminRights(boolean adminRights) {
		this.adminRights = adminRights;
	}

	public String getName() {
		return this.name();
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}
}
