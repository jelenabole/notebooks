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

	public Note findOne(Integer id) {
		return noteRepository.findOne(id);
	}

	public List<Note> findByUser(Integer id) {
		return noteRepository.findByUser(id);
	}

	public void save(Note note) {
		noteRepository.save(note);
	}

	// TODO - spojiti sa save:
	public void update(Note note) {
		noteRepository.update(note);
	}

	public void delete(Integer id) {
		noteRepository.delete(id);
	}

	@Override
	public void deleteByNotebook(Integer id) {
		noteRepository.deleteByNotebook(id);
	}

	@Override
	public void deleteByUser(Integer id) {
		noteRepository.deleteByUser(id);
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
