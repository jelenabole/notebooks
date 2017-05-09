package hr.tvz.bole.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NoteImportance {
	IMPORTANT("important", true, "noteImportance.important"),
	NOT_IMPORTANT("notImportant", false, "noteImportance.notImportant");

	private String id;
	private Boolean important;
	private String translation;

	NoteImportance(String id, Boolean important, String translation) {
		this.id = id;
		this.important = important;
		this.translation = translation;
	}

	public String getName() {
		return this.name();
	}

	// public void setName(String name) {
	// this.name = name;
	// }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTranslation() {
		return translation;
	}

	public Boolean getImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	// XXX - mapiranje iz forme:
	// dohvati status ili ako je null, onda je important
	public static NoteImportance getFormImportance(String importance) {
		return importance == null ? NoteImportance.NOT_IMPORTANT : NoteImportance.IMPORTANT;
	}

	public static Map<NoteImportance, Integer> getAsMap() {
		Map<NoteImportance, Integer> byImportance = new HashMap<>();
		byImportance.put(IMPORTANT, 0);
		byImportance.put(NOT_IMPORTANT, 0);
		return byImportance;
	}

	public static List<NoteImportance> getAllImportance() {
		List<NoteImportance> importance = Arrays.asList(NoteImportance.values()[0]);
		return importance;
	}

}
