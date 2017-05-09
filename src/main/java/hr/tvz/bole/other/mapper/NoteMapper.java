package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.Note;
import hr.tvz.bole.web.form.NoteForm;

public class NoteMapper {

	public static NoteForm mapNoteToForm(Note note) {
		NoteForm noteForm = new NoteForm();

		noteForm.setId(note.getId());
		noteForm.setUser(note.getUser());
		noteForm.setNotebook(note.getNotebook());
		noteForm.setHeader(note.getHeader());
		noteForm.setText(note.getText());

		noteForm.setImportant(note.getImportance());
		noteForm.setMark(note.getMark());
		noteForm.setStatus(note.getStatus());

		return noteForm;
	}

	public static Note mapFormToNote(NoteForm noteForm) {
		Note note = new Note();

		note.setId(noteForm.getId());
		note.setUser(noteForm.getUser());
		note.setNotebook(noteForm.getNotebook());
		note.setHeader(noteForm.getHeader());
		note.setText(noteForm.getText());

		note.setImportance(noteForm.getImportant());
		note.setMark(noteForm.getMark());
		note.setStatus(noteForm.getStatus());

		return note;
	}
}
