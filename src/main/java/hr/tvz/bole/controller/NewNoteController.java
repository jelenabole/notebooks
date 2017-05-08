package hr.tvz.bole.controller;

import java.security.Principal;
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
import hr.tvz.bole.form.NewNoteForm;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.repository.NoteRepository;
import hr.tvz.bole.repository.NotebookRepository;
import hr.tvz.bole.repository.UserRepository;
import hr.tvz.bole.service.MessageService;
import hr.tvz.bole.service.NotebookEditor;
import hr.tvz.bole.service.UserEditor;

//TODO - promijenit Note u bazi = userUsername i notebookTitle
//TODO - postaviti validacije (duljine) kakve su u bazi
//TODO - provjeriti da li sve metode iz repozitorija rade

//TODO - za važno se šalje "1" - staviti broj a ne tinyint = trenutno bool u bazi
//mapira se iz jedan i u jedan
@SessionAttributes({ "newNoteForm", "userRole", "byNotebooks", "byImportance", "lastChanged" })
@Controller
public class NewNoteController {

	private static Logger logger = LoggerFactory.getLogger(NewNoteController.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	NotebookRepository notebookRepository;
	@Autowired
	NoteRepository noteRepository;
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

	@ModelAttribute("lastChanged")
	public String getLastChanged() {
		return new String();
	}

	@ModelAttribute("userRole")
	public UserRole getUserRole() {
		return new UserRole();
	}

	@ModelAttribute("newNoteForm")
	public NewNoteForm getNewNoteForm() {
		return new NewNoteForm();
	}

	@ModelAttribute("byNotebooks")
	public Map<String, Integer> getStatsByNotebooks() {
		Map<String, Integer> byNotebooks = new HashMap<String, Integer>();
		for (Notebook notebook : notebookRepository.findAll()) {
			byNotebooks.put(notebook.getTitle(), 0);
		}
		return byNotebooks;
	}

	@ModelAttribute("byImportance")
	public Map<NoteImportance, Integer> getStatsByImportance() {
		return NoteImportance.getAsMap();
	}

	@GetMapping("/newNote")
	public String prepareNewForm(@ModelAttribute NewNoteForm newNoteForm, @ModelAttribute UserRole userRole,
			Model model, Principal principal) {

		// provjeriti korisnika i zapisati u session:
		UserRole role = new UserRole(principal.getName(), userRepository.hasAdminRole(principal.getName()));
		logger.info("GET - newNote - admin role: " + role.isAdmin());
		model.addAttribute("userRole", role);

		System.out.println("username: " + principal.getName());
		refreshForm(model, newNoteForm);

		return "newNote";
	}

	@GetMapping("/newNote/{id}")
	public String loadExistingNote(@ModelAttribute NewNoteForm newNoteForm, @PathVariable int id, Model model) {
		logger.info("GET - editNote - note id: " + id);

		mapNoteToForm(newNoteForm, noteRepository.findOne(id));

		return "redirect:/newNote";
	}

	@PostMapping("/newNote")
	public String previewAndValidateForm(@Valid NewNoteForm newNoteForm, BindingResult result, Model model) {

		// XXX - ako nije označen ni jedan radio - ostaje stara vrijednost:
		if (newNoteForm.getImportant() == null) {
			newNoteForm.setMark(null);
		}

		if (result.hasErrors()) {
			refreshForm(model, newNoteForm);
			return "newNote";
		}

		return "previewNote";
	}

	@PostMapping("/saveNote")
	public String saveForm(Model model) {
		NewNoteForm newNoteForm = (NewNoteForm) model.asMap().get("newNoteForm");
		// provjera zbog refresha stranice - form obrisan:
		if (newNoteForm.getHeader() == null) {
			return "redirect:/newNote";
		}
		logger.info("/saveNote - saving with id: " + newNoteForm.getId());

		// promjena countera i lastChanged:
		// TODO - counter, isto dohvatiti brojač:
		// String lastChanged = (String) model.asMap().get("lastChanged");
		// lastChanged = newNoteForm.getNotebook().getTitle();
		// System.out.println("last changed: " + lastChanged);
		// model.addAttribute("lastChanged", lastChanged);

		Note newNote = new Note(newNoteForm);
		System.out.println("id: " + newNoteForm.getId());
		System.out.println("note id: " + newNoteForm.getId());

		if (newNote.getId() == null) {
			noteRepository.save(newNote);
		} else {
			noteRepository.update(newNote);
		}
		model.addAttribute("note", newNote);
		model.addAttribute("newNoteForm", new NewNoteForm());

		return "saveNote";
	}

	@GetMapping("/saveNote")
	public String redirect() {
		return "redirect:/newNote";
	}

	@GetMapping("/deleteForm")
	public String deleteForm(Model model) {
		model.addAttribute("newNoteForm", new NewNoteForm());
		return "redirect:/newNote";
	}

	@GetMapping("/end-session")
	public String endSession(SessionStatus status) {
		status.setComplete();
		return "redirect:/newNote";
	}

	/**
	 * Dodavanje svih potrebnih lista na ekran, ovisno o pravima korisnika.
	 * 
	 * @param model
	 * @param newNoteForm
	 *            - zbog popunjavanja korisničkog imena (ukoliko nije admin)
	 */
	private void refreshForm(Model model, NewNoteForm newNoteForm) {
		// TODO - pazit na dohvaćanje password-a
		UserRole role = (UserRole) model.asMap().get("userRole");
		List<Note> listOfNotes = null;
		if (role.isAdmin()) {
			model.addAttribute("userList", userRepository.findAll());
			listOfNotes = noteRepository.findAll();
		} else {
			newNoteForm.setUser(userRepository.findOne(role.getUsername()));
			listOfNotes = noteRepository.findByUsername(role.getUsername());
		}
		model.addAttribute("notebookList", notebookRepository.findAll());
		model.addAttribute("listOfNotes", listOfNotes);
		fillStats(model, listOfNotes);
	}

	/**
	 * Filtriranje za statistiku (po bilježnicama i važnosti).
	 * 
	 * @param model
	 * @param listOfNotes
	 */
	private void fillStats(Model model, List<Note> listOfNotes) {
		Map<NoteImportance, Integer> byImportance = getStatsByImportance();
		Map<String, Integer> byNotebooks = getStatsByNotebooks();

		listOfNotes.forEach(e -> {
			NoteImportance imp = NoteImportance.setImprotance(e.getImportant());
			byImportance.put(imp, byImportance.get(imp) + 1);
			byNotebooks.put(e.getNotebook().getTitle(), byNotebooks.get(e.getNotebook().getTitle()) + 1);
		});

		model.addAttribute("byImportance", byImportance);
		model.addAttribute("byNotebooks", byNotebooks);
	}

	/**
	 * Mapira Note objekt na newNoteForm objekt (prilikom editiranja bilješke).
	 * 
	 * @param newNoteForm
	 *            - forma za unos (promjenu) podataka
	 * @param note
	 *            - postojeći Note objekt koji se želi editirati
	 */
	private void mapNoteToForm(NewNoteForm newNoteForm, Note note) {
		// TODO - popravit mapiranje mark-a (kod update-a ostaje isto)
		// TODO - important - komplikacija zbog true/false i 1/null
		newNoteForm.setId(note.getId());
		newNoteForm.setUser(note.getUser());
		newNoteForm.setNotebook(note.getNotebook());
		newNoteForm.setHeader(note.getHeader());
		newNoteForm.setText(note.getText());
		if (note.getImportant() != null) {
			newNoteForm.setImportant(note.getImportant() == true ? "1" : null);
			newNoteForm.setMark(note.getMark());
		}
	}
}
