package hr.tvz.bole.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import hr.tvz.bole.NewNoteForm;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;
import hr.tvz.bole.service.AppService;

@SessionAttributes({ "newNoteForm", "counter", "lastChanged" })
@Controller
public class NewNoteController {

	@Autowired
	public AppService appService;

	@ModelAttribute("lastChanged")
	public String getLastChanged() {
		return "";
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

	@GetMapping("/newNote")
	public String getNewForm(Model model) {
		model.addAttribute("userList", appService.findAllUsers());
		model.addAttribute("notebookList", appService.findAllNotebooks());

		return "newNote";
	}

	@PostMapping("/newNote")
	// XXX - zbog validacije sam morala ostaviti newNoteForm kao argument
	public String previewAndValidateForm(@Valid NewNoteForm newNoteForm, BindingResult result, Model model) {

		// get user info:
		User noteUser = appService.findUserById(newNoteForm.getUserId());
		// XXX - 2 uvjeta za provjeru, radi razlièitih grešaka
		if (newNoteForm.getUserId() != null && noteUser == null) {
			result.rejectValue("userId", "userId.wrongId");
		}

		// get notebook info:
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		if (newNoteForm.getNotebookId() != null && noteNotebook == null) {
			result.rejectValue("notebookId", "notebook.wrongId");
		}

		// check errors:
		if (result.hasErrors()) {
			model.addAttribute("userList", appService.findAllUsers());
			model.addAttribute("notebookList", appService.findAllNotebooks());
			return "newNote";
		}

		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText());
		model.addAttribute("note", newNote);

		return "previewNote";
	}

	// XXX - probati dohvatiti samo Note - veæ stvoren
	@PostMapping("/saveNote")
	public String saveForm(Model model) {
		NewNoteForm newNoteForm = (NewNoteForm) model.asMap().get("newNoteForm");
		// potrebno zbog refresha stranice:
		if (newNoteForm.getHeader() == null) { // form je obrisan
			System.out.println("newNote ne postoji");
			return "redirect:/newNote";
		}

		// Note objekt - validacije preskoèene:
		User noteUser = appService.findUserById(newNoteForm.getUserId());
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		Note note = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText());
		model.addAttribute("note", note);

		// promjena countera i lastChanged:
		@SuppressWarnings("unchecked")
		Map<String, Integer> counter = (Map<String, Integer>) model.asMap().get("counter");
		String lastChanged = (String) model.asMap().get("lastChanged");

		String title = noteNotebook.getTitle();
		counter.put(title, counter.get(title) + 1);
		lastChanged = title;
		model.addAttribute("lastChanged", lastChanged);

		// XXX - forma je resetirana na sljedeæi naèin:
		model.addAttribute("newNoteForm", new NewNoteForm());

		return "saveNote";
	}

	// XXX - lastChanged se pošalje kao get objekt (u linku) ???
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

	// XXX - sa ovim sessionAtributima mi ponekad piše u linku:
	// XXX - /newNote;jsessionid=8459AD99D1877D920376F186F90E66BD
	@PostMapping("/saveNote-druga")
	public String saveFormDrugaTest(@SessionAttribute NewNoteForm newNoteForm,
			@SessionAttribute Map<String, Integer> counter, @SessionAttribute String lastChanged,
			@ModelAttribute Note note, Model model) {

		if (newNoteForm == null) { // form = obrisan
			return "redirect:/newNote";
		}

		if (note != null) {
			// XXX - note nije null, ali podaci unutar njega jesu
			System.out.println(note); // note je u obliku model.Note@527283de
			System.out.println("naslov (string): " + note.getHeader());
			System.out.println("bilježnica (objekt): " + note.getNotebook());
		}

		User noteUser = appService.findUserById(newNoteForm.getUserId());
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText());
		model.addAttribute("note", newNote);

		String title = noteNotebook.getTitle();
		counter.put(title, counter.get(title) + 1);
		lastChanged = title;
		model.addAttribute("lastChanged", lastChanged);

		model.addAttribute("newNoteForm", new NewNoteForm());

		return "saveNote";
	}
}
