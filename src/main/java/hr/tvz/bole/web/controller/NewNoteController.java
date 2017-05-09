package hr.tvz.bole.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import hr.tvz.bole.enums.NoteImportance;
import hr.tvz.bole.model.CurrentUser;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;
import hr.tvz.bole.server.service.MessageService;
import hr.tvz.bole.server.service.NoteService;
import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.server.service.RoleService;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.editors.NotebookEditor;
import hr.tvz.bole.web.editors.UserEditor;
import hr.tvz.bole.web.form.NoteForm;

@Controller
@SessionAttributes({ "currentUser", "noteForm", "byNotebooks", "byImportance" })
public class NewNoteController {

	private static Logger logger = LoggerFactory.getLogger(NewNoteController.class);

	@Autowired
	UserService userService;
	@Autowired
	NotebookService notebookService;
	@Autowired
	NoteService noteService;
	@Autowired
	RoleService roleService;

	@Autowired
	UserEditor userEditor;
	@Autowired
	NotebookEditor notebookEditor;
	@Autowired
	MessageService message;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.registerCustomEditor(User.class, userEditor);
		binder.registerCustomEditor(Notebook.class, notebookEditor);
	}

	@ModelAttribute("noteForm")
	public NoteForm getNoteForm() {
		return new NoteForm();
	}

	@ModelAttribute("byNotebooks")
	public Map<String, Integer> getStatsByNotebooks() {
		Map<String, Integer> byNotebooks = new HashMap<String, Integer>();
		for (Notebook notebook : notebookService.findAll()) {
			byNotebooks.put(notebook.getTitle(), 0);
		}
		return byNotebooks;
	}

	@ModelAttribute("byImportance")
	public Map<NoteImportance, Integer> getStatsByImportance() {
		return NoteImportance.getAsMap();
	}

	@GetMapping("/newNote")
	public String prepareNewForm(@ModelAttribute NoteForm noteForm, Model model) {

		logger.info("GET - newNote");
		refreshForm(model, noteForm);

		return "newNote";
	}

	@GetMapping("/newNote/{id}")
	public String loadExistingNote(@ModelAttribute NoteForm noteForm, @PathVariable int id,
			Model model) {
		logger.info("GET - editNote - note id: " + id);

		model.addAttribute("noteForm", noteService.getOneAsForm(id));
		return "redirect:/newNote";
	}

	@PostMapping("/newNote")
	public String previewAndValidateForm(@Valid NoteForm noteForm, BindingResult result,
			Model model) {
		logger.info("VALIDATE - previewNote");

		// XXX - ako nije oznacen ni jedan radio - nema oznake:
		// TODO - provjeriti, ne bi trebalo biti potrebno:
		if (noteForm.getImportant() == null) {
			System.out.println("VALIDATE - important == Null");
			noteForm.setMark(null);
		}
		if (result.hasErrors()) {
			refreshForm(model, noteForm);
			return "newNote";
		}
		logger.info("POST - previewNote - note header: " + noteForm.getHeader());

		return "previewNote";
	}

	@PostMapping("/saveNote")
	public String saveForm(Model model) {
		NoteForm noteForm = (NoteForm) model.asMap().get("noteForm");
		// provjera zbog refresha stranice - form obrisan:
		if (noteForm.getHeader() == null) {
			logger.info("POST - saveNote - ERROR (note = null) - redirect");
			return "redirect:/newNote";
		}
		logger.info("POST - saveNote - saving with id: " + noteForm.getId());

		model.addAttribute("note", noteService.save(noteForm));
		model.addAttribute("noteForm", new NoteForm());

		return "saveNote";
	}

	@GetMapping("/saveNote")
	public String redirect() {
		logger.info("GET - saveNote - ERROR (note = null) - redirect");
		return "redirect:/newNote";
	}

	@GetMapping("/deleteForm")
	public String deleteForm(Model model) {
		logger.info("GET - deleteForm");
		model.addAttribute("noteForm", new NoteForm());
		return "redirect:/newNote";
	}

	@GetMapping("/end-session")
	public String endSession(SessionStatus status) {
		logger.info("GET - endSession - ERROR - not implemented - throw NullPointerException");
		status.setComplete();
		throw new NullPointerException();
		// return "redirect:/newNote";
	}

	/**
	 * Dodavanje svih potrebnih lista na ekran, ovisno o pravima korisnika.
	 * 
	 * @param model
	 * @param noteForm
	 *            - zbog popunjavanja korisnickog imena (ukoliko nije admin)
	 */
	private void refreshForm(Model model, NoteForm noteForm) {
		CurrentUser currentUser = (CurrentUser) model.asMap().get("currentUser");

		model.addAttribute("userList", userService.findAll());

		List<Note> listOfNotes = noteService.getAllPermitted(currentUser);
		if (!currentUser.isAdmin()) {
			noteForm.setUser(userService.findOne(currentUser.getId()));
		}

		model.addAttribute("notebookList", notebookService.findAll());
		model.addAttribute("listOfNotes", listOfNotes);
		model.addAttribute("noteImportance", NoteImportance.getAllImportance());
		fillStats(model, listOfNotes);
	}

	/**
	 * Filtriranje za statistiku (po biljeznicama i vaznosti).
	 * 
	 * @param model
	 * @param listOfNotes
	 */
	private void fillStats(Model model, List<Note> listOfNotes) {
		Map<NoteImportance, Integer> byImportance = getStatsByImportance();
		Map<String, Integer> byNotebooks = getStatsByNotebooks();

		listOfNotes.forEach(e -> {
			// TODO - prvi red nepotreban:
			NoteImportance imp = e.getImportance();
			byImportance.put(imp, byImportance.get(imp) + 1);
			byNotebooks.put(e.getNotebook().getTitle(),
					byNotebooks.get(e.getNotebook().getTitle()) + 1);
		});

		model.addAttribute("byImportance", byImportance);
		model.addAttribute("byNotebooks", byNotebooks);
	}

}
