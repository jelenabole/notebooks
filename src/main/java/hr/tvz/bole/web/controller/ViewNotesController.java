package hr.tvz.bole.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.enums.NoteImportance;
import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.editors.NotebookEditor;
import hr.tvz.bole.web.editors.UserEditor;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.NoteForm;

@Controller
@SessionAttributes({ "currentUser", "filterForm" })
public class ViewNotesController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotesController.class);

	@Autowired
	NoteService noteService;
	@Autowired
	UserService userService;
	@Autowired
	NotebookService notebookService;

	@Autowired
	UserEditor userEditor;
	@Autowired
	NotebookEditor notebookEditor;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.registerCustomEditor(User.class, userEditor);
		binder.registerCustomEditor(Notebook.class, notebookEditor);
	}

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

	private void putInfoInModel(Model model) {
		// info za editiranje:
		model.addAttribute("userList", userService.findAll());
		model.addAttribute("notebookList", notebookService.findAll());
		model.addAttribute("noteImportance", NoteImportance.getAllImportance());
	}

	@GetMapping("/viewNotes")
	public String getNotes(@SessionAttribute CurrentUser currentUser, Model model) {
		logger.info("GET - view notes - get by role (" + currentUser.getRoles() + ")");

		// create filter form:
		model.addAttribute("filterForm", getFilterForm(model));

		return "viewNotes";
	}

	/**** OSTALE METODE VRAĆAJU FRAGMENTE STRANICE ****/

	// XXX - AJAX - edit note:
	@GetMapping("/note/edit/{id}")
	@Secured("ROLE_ADMIN")
	public String loadNote(@PathVariable Integer id, Model model) {
		logger.info("EDIT - view notes");
		model.addAttribute("noteForm", noteService.getOneAsForm(id));

		putInfoInModel(model);
		return "fragments/forms :: noteForm";
	}

	// XXX - AJAX - new note:
	@GetMapping("/note/new")
	@Secured("ROLE_ADMIN")
	public String createNote(Model model) {
		logger.info("NEW - view notes");
		model.addAttribute("noteForm", new NoteForm());

		putInfoInModel(model);
		return "fragments/forms :: noteForm";
	}

	// XXX - AJAX - remove form (cancel):
	@GetMapping("/note/removeForm")
	@Secured("ROLE_ADMIN")
	public String removeForm(Model model) {
		logger.info("REMOVE FORM - view notes");
		model.addAttribute("noteForm", new NoteForm());

		return "fragments/forms :: empty";
	}

	// XXX - AJAX - save note:
	@PostMapping("/note/save")
	@Secured("ROLE_ADMIN")
	public String saveNote(@Valid @RequestBody NoteForm noteForm, BindingResult result,
			Model model) {
		logger.info("VALIDATE - view notes");

		if (result.hasErrors()) {
			putInfoInModel(model);
			model.addAttribute("noteForm", noteForm);
			return "fragments/forms :: noteForm";
		}

		logger.info("SAVE - view notes");

		noteService.save(noteForm);
		return "fragments/forms :: empty";
	}

	// XXX - AJAX - filter/sort:
	@PostMapping("/notes/search")
	public String searchNotes(@RequestBody FilterForm filterForm,
			@SessionAttribute CurrentUser currentUser, Model model) {
		logger.info("GET/POST - search notes - get by role (" + currentUser.getRoles() + ")");

		model.addAttribute("notes", noteService.getFilteredNotes(filterForm, currentUser));

		return "fragments/tables :: viewNotesTable";
	}

}
