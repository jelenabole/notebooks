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

@SessionAttributes({ "newNoteForm", "counter", "lastChanged", "noteImportance" })
@Controller
public class NewNoteController {

	@Autowired
	public AppService appService;

	@ModelAttribute("lastChanged")
	public String getLastChanged() {
		return "";
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

	@GetMapping("/newNote")
	public String getNewForm(Model model) {
		model.addAttribute("userList", appService.findAllUsers());
		model.addAttribute("notebookList", appService.findAllNotebooks());

		return "newNote";
	}

	@PostMapping("/newNote")
	public String previewAndValidateForm(@Valid NewNoteForm newNoteForm, BindingResult result, Model model) {

		// get user info:
		User noteUser = appService.findUserById(newNoteForm.getUserId());
		if (!newNoteForm.getUserId().isEmpty() && noteUser == null) {
			result.rejectValue("userId", "userId.wrongId");
		}

		// get notebook info:
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		if (!newNoteForm.getNotebookId().isEmpty() && noteNotebook == null) {
			result.rejectValue("notebookId", "notebook.wrongId");
		}

		// check errors:
		if (result.hasErrors()) {
			model.addAttribute("userList", appService.findAllUsers());
			model.addAttribute("notebookList", appService.findAllNotebooks());
			return "newNote";
		}

		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText(),
				newNoteForm.getImportance() != null);
		model.addAttribute("note", newNote);

		return "previewNote";
	}

	@PostMapping("/saveNote")
	public String saveForm(Model model) {
		NewNoteForm newNoteForm = (NewNoteForm) model.asMap().get("newNoteForm");
		// potrebno zbog refresha stranice - form obrisan:
		if (newNoteForm.getHeader() == null) {
			return "redirect:/newNote";
		}

		@SuppressWarnings("unchecked")
		Map<String, Integer> noteImportance = (Map<String, Integer>) model.asMap().get("noteImportance");
		if (newNoteForm.getImportance() == null) {
			noteImportance.put("Nevažne bilješke", noteImportance.get("Nevažne bilješke") + 1);
		} else {
			noteImportance.put("Važne bilješke", noteImportance.get("Važne bilješke") + 1);
		}
		model.addAttribute("noteImportance", noteImportance);

		// Note objekt - validacije preskoæene:
		User noteUser = appService.findUserById(newNoteForm.getUserId());
		Notebook noteNotebook = appService.findNotebookById(newNoteForm.getNotebookId());
		Note note = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText(),
				newNoteForm.getImportance() != null);

		model.addAttribute("note", note);

		// promjena countera i lastChanged:
		@SuppressWarnings("unchecked")
		Map<String, Integer> counter = (Map<String, Integer>) model.asMap().get("counter");
		String lastChanged = (String) model.asMap().get("lastChanged");

		String title = noteNotebook.getTitle();
		counter.put(title, counter.get(title) + 1);
		lastChanged = title;
		model.addAttribute("lastChanged", lastChanged);

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
