package hr.tvz.bole.web.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.web.form.FilterForm;

@Controller
@SessionAttributes({ "currentUser", "filterForm" })
public class ViewNotesController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotesController.class);

	@Autowired
	NoteService noteService;

	@ModelAttribute("filterForm")
	public FilterForm getFilterForm(Model model) {
		CurrentUser currentUser = (CurrentUser) model.asMap().get("currentUser");

		List<String> orderByList;
		if (currentUser.isAdmin())
			orderByList = Arrays.asList("userName", "userSurname", "notebook", "header", "text",
					"importance", "mark", "status");
		else
			orderByList = Arrays.asList("notebook", "header", "text", "importance", "mark");
		String objectName = "note";
		return new FilterForm(orderByList, objectName);
	}

	@GetMapping("/viewNotes")
	public String getNewForm(@SessionAttribute CurrentUser currentUser, Model model) {
		logger.info("GET - view notes - get by role (" + currentUser.getRoles() + ")");

		// poslati za filtere:
		model.addAttribute("filterForm", getFilterForm(model));

//		model.addAttribute("notes", noteService.getAllPermitted(currentUser));

		return "viewNotes";
	}
	
	//XXX - AJAX - filter/sort - fragment:
	@PostMapping("/notes/search")
	public String searchNotes(@SessionAttribute CurrentUser currentUser,
			@RequestBody FilterForm filterForm, Model model) {
		logger.info("GET/POST - search notes - get by role (" + currentUser.getRoles() + ")");
		
		model.addAttribute("notes", noteService.getNotesAjax(filterForm, currentUser));

		return "fragments/tables :: viewNotesTable";
	}

	@PostMapping("/viewNotes")
	public String filterNotes(@SessionAttribute CurrentUser currentUser,
			@ModelAttribute FilterForm filterForm, Model model) {
		logger.info("GET - view notes - get by role (" + currentUser.getRoles() + ")");

		// poslati za filtere:
		// filterNotes(filterForm, currentUser);
		noteService.getFilteredNotes(filterForm, currentUser);

		model.addAttribute("notes", noteService.getFilteredNotes(filterForm, currentUser));

		return "viewNotes";
	}

	@GetMapping("/viewNotes/{order}")
	public String getNewForm(@SessionAttribute CurrentUser currentUser, Model model,
			@PathVariable String order) {
		logger.info("SORT - notes - get by role (" + currentUser.getRoles() + ")");

		if (order.equals("asc"))
			model.addAttribute("notes", noteService.getAllPermittedSorted(currentUser));
		else if (order.equals("desc"))
			model.addAttribute("notes", noteService.getAllPermittedSortedDesc(currentUser));
		else { // XXX - fallback:
			model.addAttribute("notes", noteService.getAllPermitted(currentUser));
		}

		return "viewNotes";
	}

	@GetMapping("/deleteNote/{id}")
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
