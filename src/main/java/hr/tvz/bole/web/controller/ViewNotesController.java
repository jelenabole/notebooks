package hr.tvz.bole.web.controller;

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
import hr.tvz.bole.server.service.NoteService;

@Controller
@SessionAttributes({ "user", "userRole" })
public class ViewNotesController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotesController.class);

	@Autowired
	NoteService noteService;

	@GetMapping("/viewNotes")
	public String getNewForm(@ModelAttribute UserRole userRole, Model model) {
		logger.info("GET - view notes - get by role (" + userRole.getRole() + ")");

		model.addAttribute("notes", noteService.getAllPermitted(userRole));

		return "viewNotes";
	}

	@GetMapping("/viewNotes/{order}")
	public String getNewForm(@ModelAttribute UserRole userRole, Model model,
			@PathVariable String order) {
		logger.info("SORT - notes - get by role (" + userRole.getRole() + ")");

		if (order.equals("asc"))
			model.addAttribute("notes", noteService.getAllPermittedSorted(userRole));
		else
			model.addAttribute("notes", noteService.getAllPermittedSortedDesc(userRole));

		return "viewNotes";
	}

	@GetMapping("/viewNotes/{id}")
	public String getNewForm(Model model, @PathVariable int id) {
		logger.info("DELETE - note id: " + id);
		noteService.delete(id);
		return "redirect:/viewNotes";
	}

	@GetMapping("/changeNoteStatus/{id}")
	public String changeStatus(Model model, @PathVariable int id) {
		logger.info("UPDATE - change status - note id: " + id);
		noteService.changeNoteStatus(id);
		return "redirect:/viewNotes";
	}

}
