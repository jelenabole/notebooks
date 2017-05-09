package hr.tvz.bole.enums;

public enum DBStatus {
	ACTIVE(true, "status.active"),
	NOT_ACTIVE(false, "status.notActive");

	private Boolean active; // TODO - da li se prikazuje
	private String translation;

	DBStatus(Boolean active, String translation) {
		this.active = active;
		this.translation = translation;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

}
