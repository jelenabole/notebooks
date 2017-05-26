package hr.tvz.bole.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.enums.DBStatus;
import hr.tvz.bole.enums.NoteImportance;
import hr.tvz.bole.enums.NoteMark;
import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.AdminNoteForm;
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
	public String getNewForm(@ModelAttribute NoteForm noteForm,
			@SessionAttribute CurrentUser currentUser, Model model) {
		logger.info("GET - view notes - get by role (" + currentUser.getRoles() + ")");

		// model.addAttribute("notes",
		// noteService.getAllPermitted(currentUser));

		// poslati za filtere:
		model.addAttribute("filterForm", getFilterForm(model));

		// info za editiranje ili novu bilješku:
		model.addAttribute("userList", userService.findAll());
		model.addAttribute("notebookList", notebookService.findAll());
		model.addAttribute("noteImportance", NoteImportance.getAllImportance());

		return "viewNotes";
	}

	// XXX - AJAX - edit note:
	@GetMapping("/note/edit/{id}")
	public String editNote(@PathVariable Integer id, Model model) {
		logger.info("EDIT - view notes - get by role (" + ")");
		model.addAttribute("noteForm", noteService.getOneAsForm(id));

		model.addAttribute("userList", userService.findAll());
		model.addAttribute("notebookList", notebookService.findAll());
		model.addAttribute("noteImportance", NoteImportance.getAllImportance());
		return "fragments/forms :: noteForm";
	}

	// XXX - AJAX - new note:
	@GetMapping("/note/new")
	public String newNote(Model model) {
		logger.info("NEW - view notes - get by role (" + ")");
		model.addAttribute("noteForm", new NoteForm());

		model.addAttribute("userList", userService.findAll());
		model.addAttribute("notebookList", notebookService.findAll());
		model.addAttribute("noteImportance", NoteImportance.getAllImportance());
		return "fragments/forms :: noteForm";
	}

	// XXX - AJAX - save note:
	@PostMapping("/note/save")
	public String saveNote(@Valid @RequestBody AdminNoteForm noteForm, BindingResult result,
			Model model) {
		logger.info("EDIT - view notes - get by role (" + ")");

		System.out.println(noteForm);
		NoteForm resultForm = noteFormAdminToNoteForm(noteForm);
		System.out.println(resultForm);

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			System.out.println(result.getFieldErrorCount("user"));
			model.addAttribute("userList", userService.findAll());
			model.addAttribute("notebookList", notebookService.findAll());
			model.addAttribute("noteImportance", NoteImportance.getAllImportance());
			model.addAttribute("noteForm", resultForm);
			// kako vratiti grešku i onda gornji fragment (formu)
			return "fragments/forms :: noteForm";
		}

		// noteService.save(noteForm);

		// nepotrebno?:
		// model.addAttribute("noteForm", new NoteForm());

		model.addAttribute("userList", userService.findAll());
		model.addAttribute("notebookList", notebookService.findAll());
		model.addAttribute("noteImportance", NoteImportance.getAllImportance());
		return "fragments/tables :: viewNotesTable";
	}

	private NoteForm noteFormAdminToNoteForm(AdminNoteForm noteForm) {
		// TODO Auto-generated method stub
		NoteForm note = new NoteForm();
		note.setId(noteForm.getId());
		note.setUser(userService.findOne(noteForm.getUser()));
		note.setNotebook(notebookService.findOne(noteForm.getNotebook()));

		note.setHeader(noteForm.getHeader());
		note.setText(noteForm.getText());
		// promijenjeno
		if (noteForm.getImportant() == null) {
			note.setImportant(NoteImportance.NOT_IMPORTANT);
		} else
			note.setImportant(NoteImportance.valueOf(noteForm.getImportant()));
		if (noteForm.getMark() == null) {
			note.setMark(null);
		} else
			note.setMark(NoteMark.valueOf(noteForm.getMark()));
		note.setStatus(DBStatus.ACTIVE);

		return note;
	}

	// XXX - AJAX - filter/sort - fragment:
	@PostMapping("/notes/search")
	public String searchNotes(@SessionAttribute CurrentUser currentUser,
			@RequestBody FilterForm filterForm, Model model) {
		logger.info("GET/POST - search notes - get by role (" + currentUser.getRoles() + ")");

		model.addAttribute("notes", noteService.getNotesAjax(filterForm, currentUser));

		return "fragments/tables :: viewNotesTable";
	}

}
