package hr.tvz.bole.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.model.Note;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.UserService;

@RestController
@RequestMapping("/api/note")
@SessionAttributes({ "currentUser" })
public class NoteRestController {

	private static Logger logger = LoggerFactory.getLogger(NoteRestController.class);

	@Autowired
	NoteService noteService;
	@Autowired
	UserService userService;

	@GetMapping
	public List<Note> findAll() {
		return noteService.findAll();
	}

	@GetMapping("/{id}")
	public Note findOne(@PathVariable Integer id) {
		return noteService.findOne(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Note save(@RequestBody Note note) {
		return noteService.save(note);
	}

	// XXX - isto je kao save sa postojećim ID-em:
	@PutMapping("/{id}")
	public Note update(@RequestBody Note note) {
		return noteService.save(note);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		noteService.delete(id);
	}

	@GetMapping("/changeStatus/{id}")
	public void changeStatus(@PathVariable Integer id) {
		logger.info("UPDATE - change status - note id: " + id);
		noteService.changeNoteStatus(id);
	}

	// XXX - dodatno za labos
	@GetMapping("/broj")
	public Page<Note> findNumberOf(@RequestParam("broj") Integer numberOfNotes) {
		logger.info("UPDATE - dohvati bilješke: " + numberOfNotes);
		return noteService.getFirstFew(numberOfNotes);
	}

}
