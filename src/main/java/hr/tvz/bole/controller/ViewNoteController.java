package hr.tvz.bole.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.model.Note;

@Controller
@SessionAttributes({ "listOfNotes" })
public class ViewNoteController {

	@GetMapping("/viewNotes")
	public String getNewForm(@ModelAttribute ArrayList<Note> listOfNotes, Model model) {
		
		
		// postavljanje user-a u select:

		return "viewNotes";
	}

}
