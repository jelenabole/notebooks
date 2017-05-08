package hr.tvz.bole.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.bole.model.Note;
import hr.tvz.bole.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository noteRepository;

	public List<Note> findAll() {
		return noteRepository.findAll();
	}

	public Note findById(Integer id) {
		return noteRepository.findOne(id);
	}

	public List<Note> findByUsername(String username) {
		return noteRepository.findByUsername(username);
	}

	public Note save(Note note) {
		note.setId(noteRepository.save(note));
		return note;
	}

	public Integer update(Note note) {
		return noteRepository.update(note);
	}

	public void delete(Integer id) {
		noteRepository.delete(id);
	}
}
