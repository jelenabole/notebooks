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
	public String newNoteForm(Model model) {
		model.addAttribute("userList", appService.findAllUsers());
		model.addAttribute("notebookList", appService.findAllNotebooks());
		return "newNote";
	}

	@PostMapping("/newNote")
	public String processForm(@Valid NewNoteForm newNoteForm, BindingResult result, Model model) {
		
		// get user info:
		User noteUser = appService.findUserById(newNoteForm.getUserId());
		if (noteUser == null && newNoteForm.getUserId() != null) {
			result.rejectValue("userId", "userId.wrongId");
		}

		// get notebook info:
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		if (noteNotebook == null && newNoteForm.getNotebookId() != null) {
			result.rejectValue("notebookId", "notebook.wrongId");
		}

		if (result.hasErrors()) {
			model.addAttribute("userList", appService.findAllUsers());
			model.addAttribute("notebookList", appService.findAllNotebooks());
			return "newNote";
		}

		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText());
		model.addAttribute("note", newNote);

		return "notePreview";
	}

	@GetMapping("/note")
	public String previewForm(Model model) { // ne postoji NOTE u modelu!
		System.out.println("note u modelu: " + model.asMap().get("note"));
		
		// potrebni podaci iz modela:
		NewNoteForm newNoteForm = (NewNoteForm) model.asMap().get("newNoteForm");
		@SuppressWarnings("unchecked")
		Map<String, Integer> counter = (Map<String, Integer>) model.asMap().get("counter");

		// TODO - nepotrebne provjere, veæ su napravljene na ekranu ispred..
		User noteUser = appService.findUserById(newNoteForm.getUserId());
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText());
		model.addAttribute("note", newNote);

		// updateNumberOfNotes(newNoteForm.getHeader());

		// promjena countera (broji bilješke u bilježnicama)
		// lastChanged = oznaèava zadnji promijenjeni objekt
		counter.put(noteNotebook.getTitle(), counter.get(noteNotebook.getTitle()) + 1);
		model.addAttribute("lastChanged", noteNotebook.getTitle());

		// TODO - sprema se u bazu, potrebno obrisati postojeæi objekt:
		// objekt je inicijaliziran preko @ModelAttribute funkcije gore, a briše
		// se na ovaj naèin:
		model.addAttribute("newNoteForm", new NewNoteForm());

		return "note";
	}
	
	@PostMapping("/note")
	public String saveForm(@ModelAttribute Note note, Model model) { // ne postoji NOTE u modelu!
		// potrebni podaci iz modela - counter:
		@SuppressWarnings("unchecked")
		Map<String, Integer> counter = (Map<String, Integer>) model.asMap().get("counter");

		// updateNumberOfNotes(newNoteForm.getHeader());
		if (note != null) {
			System.out.println(note);
			System.out.println(note.getNotebook());
			System.out.println(note.getHeader());
		}
		
		counter.put(note.getNotebook().getTitle(), counter.get(note.getNotebook().getTitle()) + 1);
		model.addAttribute("lastChanged", note.getNotebook().getTitle());

		model.addAttribute("newNoteForm", new NewNoteForm());

		return "note";
	}

	// TODO - prenijeti na notePreview, zatim na note i obrisati objekt

	@GetMapping("/end-session")
	public String endSession(SessionStatus status) {
		status.setComplete();
		return "redirect:/newNote";
	}

}
