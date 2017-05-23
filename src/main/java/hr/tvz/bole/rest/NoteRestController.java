package hr.tvz.bole.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.FilterForm;

@RestController
@RequestMapping("/api/note")
@SessionAttributes({ "currentUser" })
public class NoteRestController {

	private static Logger logger = LoggerFactory.getLogger(NoteRestController.class);

	@Autowired
	NoteService noteService;
	@Autowired
	UserService userService;

	// @GetMapping
	// public List<Note> findAll() {
	// return noteService.findAll();
	// }

	@PostMapping
	public List<Note> search(@RequestBody FilterForm filterForm,
			@SessionAttribute CurrentUser currentUser) {
		logger.info("REST - get Notes - current user: " + currentUser.getUsername());
		CurrentUser user = userService.getCurrentUser(currentUser.getUsername());
		// XXX - vraća se bez filtriranja po tekstu (searchBy):
		return noteService.getNotesAjax(filterForm, user);
	}

	@GetMapping("/{id}")
	public Note findOne(@PathVariable Integer id) {
		return noteService.findOne(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/save")
	public Note save(@RequestBody Note note) {
		return noteService.save(note);
	}

	@PutMapping("/{id}")
	public Note update(@RequestBody Note note) {
		return noteService.save(note);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		noteService.delete(id);
	}

}
