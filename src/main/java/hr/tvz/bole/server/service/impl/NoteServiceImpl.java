package hr.tvz.bole.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.bole.model.Note;
import hr.tvz.bole.server.repository.NoteRepository;
import hr.tvz.bole.server.service.NoteService;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteRepository noteRepository;

	public List<Note> findAll() {
		return noteRepository.findAll();
	}

	public List<Note> findAllActive() {
		return noteRepository.findAllByStatusTrue();
	}

	public Note findOne(Integer id) {
		return noteRepository.findById(id);
	}

	public List<Note> findByUser(Integer id) {
		return noteRepository.findByUserId(id);
	}

	public void save(Note note) {
		noteRepository.save(note);
	}

	// TODO - spojiti sa save:
	public void update(Note note) {
		// TODO - promjenilo se u save = isto?!?
		noteRepository.save(note);
	}

	public void delete(Integer id) {
		noteRepository.deleteById(id);
	}

	@Override
	public void deleteByNotebook(Integer id) {
		noteRepository.deleteByNotebookId(id);
	}

	@Override
	public void deleteByUser(Integer id) {
		noteRepository.deleteByUserId(id);
	}

	@Override
	public Integer getNumberOfNotes(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getNumberOfNotesForUser(String title, String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
