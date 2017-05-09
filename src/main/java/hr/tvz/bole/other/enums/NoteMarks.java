package hr.tvz.bole.other.enums;

import java.util.Arrays;
import java.util.List;

public enum NoteMarks {
	DEFAULT ("noteMarks.default", "default", "border-color: black; background-color: #F6F9FC;"),
	PURPLE ("noteMarks.purple", "purple", "border-color: #7C3889; background-color: #E9D3ED;"),
	BLUE ("noteMarks.blue", "blue", "border-color: #3e2ecc;	background-color: #bac3f4;"),
	RED ("noteMarks.red", "red", "border-color: #f71914; background-color: #f9bcbb;"),
	ORANGE ("noteMarks.orange", "orange", "border-color: #ff4c00; background-color: #ffa977;"),
	GREEN ("noteMarks.green", "green", "border-color: #2acc35; background-color: #b0ff91;");

	private String translation;
	private String name;
	private String style;

	NoteMarks (String translation, String name, String style) {
		this.translation = translation;
		this.name = name;
		this.style = style;
    }

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public static List<NoteMarks> getAllMarks() {
		List<NoteMarks> marks = Arrays.asList(NoteMarks.values());
		return marks;
	}
}
