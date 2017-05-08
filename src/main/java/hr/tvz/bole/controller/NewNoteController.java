package hr.tvz.bole.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hr.tvz.bole.MockHelper;
import hr.tvz.bole.NewNoteForm;
import hr.tvz.bole.model.Note;
import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.model.User;

@Controller
public class NewNoteController {

	@Autowired
	private ResourceBundleMessageSource messages;

	@GetMapping("/newNote")
	public String home(Model model) {
		model.addAttribute("userList", MockHelper.mockUserList());
		model.addAttribute("notebookList", MockHelper.mockNotebookList());
		model.addAttribute("newNoteForm", new NewNoteForm());
		return "newNote";
	}

	@PostMapping("/newNote")
	public String processForm(@ModelAttribute @Valid NewNoteForm newNoteForm, BindingResult result,
			Model model) {

		// prije provjere grešaka, provjeriti da li je uopæe polje prazno
		// ne prikazivati obje greške istovremeno!

		// get user info:
		User noteUser = null;
		String userId = newNoteForm.getUserId();
		if (userId != null) {
			// TODO - provjeriti da li uopæe postoji (ako je prazno, trebala se
			// veæ prikazati greška
			// TODO - provjeriti da li se pretvara u Integer
			// TODO - provjeriti da li postoji u "bazi"
			try {
				Integer id = Integer.parseInt(userId);
				for (User user : MockHelper.mockUserList()) {
					if (user.getId().equals(id)) {
						noteUser = user;
						break;
					}
				}
			} catch (Exception ex) {
				result.rejectValue("userId", "userId.wrongId");
			}
		}

		// get notebook info:
		Notebook noteNotebook = null;
		String notebookId = newNoteForm.getNotebookId();
		try {
			Integer id = Integer.parseInt(notebookId);
			for (Notebook notebook : MockHelper.mockNotebookList()) {
				if (notebook.getId().equals(id)) {
					noteNotebook = notebook;
					break;
				}
			}
		} catch (Exception ex) {
			result.rejectValue("notebookId", "notebook.wrongId");
		}

		if (result.hasErrors()) {
			model.addAttribute("userList", MockHelper.mockUserList());
			model.addAttribute("notebookList", MockHelper.mockNotebookList());
			return "newNote";
		}

		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(),
				newNoteForm.getText());
		model.addAttribute("note", newNote);

		// TODO - redirectat il ostavit isti link?
		return "note";
	}

}
