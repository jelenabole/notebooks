package hr.tvz.bole.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.repository.NoteRepository;

@Controller
@SessionAttributes({ "userRole" })
public class ViewNotesController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotesController.class);

	@Autowired
	NoteRepository noteRepository;
	
	@GetMapping("/viewNotes")
	public String getNewForm(@ModelAttribute UserRole user, Model model) {
		logger.info("GET - viewNotes - user id: " + user.getId());
		
		if (user.isAdmin()) {
			model.addAttribute("notes", noteRepository.findAll());
		} else {
			model.addAttribute("notes", noteRepository.findByUsername(user.getUsername()));
		}
		
		//filtrirati liste po atributima:

		return "viewNotes";
	}

	@GetMapping("/viewNotes/{id}")
	public String getNewForm(Model model, @PathVariable int id) {
		logger.info("GET - deleteNote id: " + id);
		noteRepository.delete(id);
		return "redirect:/viewNotes";
	}

}
