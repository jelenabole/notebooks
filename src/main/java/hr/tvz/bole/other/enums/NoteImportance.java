package hr.tvz.bole.other.enums;

import java.util.HashMap;
import java.util.Map;

public enum NoteImportance {
	IMPORTANT("noteImportance.important"),
	NOT_IMPORTANT("noteImportance.notImportant");
	
	private String importance;

	NoteImportance (String importance) {
		this.importance = importance;
    }
	
	//TODO - potrebno?! provjeriti...
	public static NoteImportance setImprotance(Boolean value) {
		if (value) {
			return IMPORTANT;
		} else {
			return NOT_IMPORTANT;
		}
	}

	public String getString() {
		return importance;
	}

	public static Map<NoteImportance, Integer> getAsMap() {
		Map<NoteImportance, Integer> byImportance = new HashMap<>();
		byImportance.put(IMPORTANT, 0);
		byImportance.put(NOT_IMPORTANT, 0);
		return byImportance;
	}
	
}
