package hr.tvz.bole.controller;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import hr.tvz.bole.form.NewNoteForm;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;
import hr.tvz.bole.service.AppService;
import hr.tvz.bole.service.NotebookEditor;
import hr.tvz.bole.service.UserEditor;

@SessionAttributes({ "newNoteForm", "counter", "lastChanged", "noteImportance", "listOfNotes" })
@Controller
public class NewNoteController {
	private static Logger logger = LoggerFactory.getLogger(NewNoteController.class);

	@Autowired
	public AppService appService;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.registerCustomEditor(User.class, new UserEditor());
		binder.registerCustomEditor(Notebook.class, new NotebookEditor());
	}

	@ModelAttribute("lastChanged")
	public String getLastChanged() {
		return new String();
	}

	@ModelAttribute("noteImportance")
	public Map<String, Integer> getImportantNotes() {
		Map<String, Integer> importance = new HashMap<String, Integer>();

		importance.put("Važne bilješke", 0);
		importance.put("Nevažne bilješke", 0);
		return importance;
	}

	@ModelAttribute("newNoteForm")
	public NewNoteForm getNewNoteForm() {
		return new NewNoteForm();
	}

	@ModelAttribute("counter")
	public Map<String, Integer> getCounter() {
		Map<String, Integer> counter = new HashMap<String, Integer>();
		for (Notebook notebook : appService.findAllNotebooks()) {
			counter.put(notebook.getTitle(), 0);
		}
		return counter;
	}
	
	@ModelAttribute("listOfNotes")
	public List<Note> getListOfNotes() {
		List<Note> listOfNotes = new ArrayList<>();
		return listOfNotes;
	}

	@GetMapping("/newNote")
	public String getNewForm(@ModelAttribute NewNoteForm newNoteForm, Model model, Principal principal) {
		model.addAttribute("userList", appService.findAllUsers());
		model.addAttribute("notebookList", appService.findAllNotebooks());

		// postavljanje user-a u select:
		User noteUser = appService.findUserByFullName(principal.getName());
		if (noteUser != null) {
			newNoteForm.setUser(noteUser);
		}
		
		return "newNote";
	}

	@PostMapping("/newNote")
	public String previewAndValidateForm(@Valid NewNoteForm newNoteForm, BindingResult result, Model model) {

		// check errors:
		if (result.hasErrors()) {
			model.addAttribute("userList", appService.findAllUsers());
			model.addAttribute("notebookList", appService.findAllNotebooks());
			return "newNote";
		}

		return "previewNote";
	}

	@PostMapping("/saveNote")
	public String saveForm(Model model, @SessionAttribute ArrayList<Note> listOfNotes) {
		NewNoteForm newNoteForm = (NewNoteForm) model.asMap().get("newNoteForm");
		// provjera zbog refresha stranice - form obrisan:
		if (newNoteForm.getHeader() == null) {
			return "redirect:/newNote";
		}

		// provjera important (radi povećanja brojača):
		@SuppressWarnings("unchecked")
		Map<String, Integer> noteImportance = (Map<String, Integer>) model.asMap().get("noteImportance");
		if (newNoteForm.getImportant() == null)
			noteImportance.put("Nevažne bilješke", noteImportance.get("Nevažne bilješke") + 1);
		else
			noteImportance.put("Važne bilješke", noteImportance.get("Važne bilješke") + 1);
		model.addAttribute("noteImportance", noteImportance);

		// promjena countera i lastChanged:
		@SuppressWarnings("unchecked")
		Map<String, Integer> counter = (Map<String, Integer>) model.asMap().get("counter");

		String lastChanged = (String) model.asMap().get("lastChanged");
		lastChanged = newNoteForm.getNotebook().getTitle();

		counter.put(lastChanged, counter.get(lastChanged) + 1);

		listOfNotes.add(new Note(newNoteForm));
		
		// note objekt i refresh forme:
		model.addAttribute("lastChanged", lastChanged);
		model.addAttribute("note", new Note(newNoteForm));
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
}
