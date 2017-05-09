package hr.tvz.bole.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.web.form.NotebookForm;

@Controller
@SessionAttributes({ "currentUser" })
public class ViewNotebooksController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotebooksController.class);

	@Autowired
	NotebookService notebookService;

	@GetMapping("/viewNotebooks")
	@Secured("ROLE_ADMIN")
	public String getNewForm(@ModelAttribute NotebookForm notebookForm, Model model) {
		logger.info("GET - view notebooks");

		model.addAttribute("notebooks", notebookService.findAllWithNumberOfNotes());

		return "viewNotebooks";
	}

	@PostMapping("/viewNotebooks")
	@Secured("ROLE_ADMIN")
	public String editExistingNotebook(@Valid NotebookForm notebookForm, BindingResult result,
			Model model) {
		logger.info("EDIT - notebook id: " + notebookForm.getId());

		if (result.hasErrors()) {
			model.addAttribute("notebooks", notebookService.findAllWithNumberOfNotes());
			return "viewNotebooks";
		}

		notebookService.save(notebookForm);

		model.addAttribute("notebooks", notebookService.findAllWithNumberOfNotes());
		model.addAttribute("notebookForm", new NotebookForm());

		return "viewNotebooks";
	}

	@GetMapping("/deleteNotebook/{id}")
	@Secured("ROLE_ADMIN")
	public String deleteNotebook(@PathVariable int id, Model model) {
		// TODO - nekakvo upozorenje prije nastavka!
		logger.info("DELETE - notebook id: " + id);
		notebookService.delete(id);
		return "redirect:/viewNotebooks";
	}

}
