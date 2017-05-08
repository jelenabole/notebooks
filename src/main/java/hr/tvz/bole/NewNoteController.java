package hr.tvz.bole;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewNoteController {

	@GetMapping("/newNote")
	public String home(Model model) {
		model.addAttribute("userList", MockHelper.mockUserList());
		model.addAttribute("notebookList", MockHelper.mockNotebookList());
		model.addAttribute("newNoteForm", new NewNoteForm());
		return "newNote";
	}

	@PostMapping("/newNote")
	public String processForm(@ModelAttribute @Valid NewNoteForm newNoteForm, 
			BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("userList", MockHelper.mockUserList());
			model.addAttribute("notebookList", MockHelper.mockNotebookList());
			return "newNote";
		}
		
		//get user info:
		User noteUser = null;
		String userId = newNoteForm.getUserId();
		try {
			Integer id = Integer.parseInt(userId);
			for (User user : MockHelper.mockUserList()) {
				if (user.getId().equals(id)) {
					noteUser = user;
					break;
				}
			}
		} catch (Exception ex) {
			//TODO - add error and mock lists
			return "newNote";
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
			//TODO - add error and mock lists
			return "newNote";
		}

		Note newNote = new Note(noteUser, noteNotebook, newNoteForm.getHeader(), newNoteForm.getText());
		model.addAttribute("note", newNote);
		
		// TODO - redirectat il ostavit isti link?
		return "note";
	}

}
