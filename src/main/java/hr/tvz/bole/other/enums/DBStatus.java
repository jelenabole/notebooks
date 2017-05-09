package hr.tvz.bole.other.enums;

public enum DBStatus {
	ACTIVE(0, "status.active"), NOT_ACTIVE(1, "status.notActive");

	private Integer active; // TODO - da li se prikazuje
	private String translation;

	DBStatus(Integer active, String translation) {
		this.active = active;
		this.translation = translation;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

}
