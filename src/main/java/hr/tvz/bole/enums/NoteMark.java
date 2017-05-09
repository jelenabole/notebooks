package hr.tvz.bole.enums;

import java.util.Arrays;
import java.util.List;

public enum NoteMark {
	DEFAULT("default", "noteMarks.default", "border-color: black; background-color: #F6F9FC;"),
	PURPLE("purple", "noteMarks.purple", "border-color: #7C3889; background-color: #E9D3ED;"),
	BLUE("blue", "noteMarks.blue", "border-color: #3e2ecc;	background-color: #bac3f4;"),
	RED("red", "noteMarks.red", "border-color: #f71914; background-color: #f9bcbb;"),
	ORANGE("orange", "noteMarks.orange", "border-color: #ff4c00; background-color: #ffa977;"),
	GREEN("green", "noteMarks.green", "border-color: #2acc35; background-color: #b0ff91;");

	private String id;
	private String translation;
	private String style;

	NoteMark(String id, String translation, String style) {
		this.id = id;
		this.translation = translation;
		this.style = style;
	}

	public String getName() {
		return this.name();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public static List<NoteMark> getAllMarks() {
		List<NoteMark> marks = Arrays.asList(NoteMark.values());
		return marks;
	}

	// public static NoteImportance getFormImportance(String importance) {
	// return importance == null ? NoteImportance.NOT_IMPORTANT :
	// NoteImportance.IMPORTANT;
	// }
}
