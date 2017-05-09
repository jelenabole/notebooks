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

import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.server.service.NotebookService;
import hr.tvz.bole.web.form.NotebookForm;

@Controller
@SessionAttributes({ "userRole" })
public class ViewNotebooksController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotebooksController.class);

	@Autowired
	NotebookService notebookService;

	@GetMapping("/viewNotebooks")
	@Secured("ROLE_ADMIN")
	public String getNewForm(@ModelAttribute NotebookForm notebookForm, @ModelAttribute UserRole userRole,
			Model model) {
		logger.info("GET - viewNotebooks");

		model.addAttribute("notebooks", notebookService.findAllWithNumberOfNotes());

		return "viewNotebooks";
	}

	@GetMapping("/viewNotebooks/{id}")
	@Secured("ROLE_ADMIN")
	public String getNewForm(Model model, @PathVariable int id) {
		// TODO - nekakvo upozorenje prije nastavka!
		logger.info("GET - deleteNotebook id: " + id);
		notebookService.delete(id);
		return "redirect:/viewNotebooks";
	}

	@PostMapping("/viewNotebooks")
	@Secured("ROLE_ADMIN")
	public String getNewForm(Model model, @Valid NotebookForm notebookForm, BindingResult result) {
		logger.info("edit form - id: " + notebookForm.getId());

		if (result.hasErrors()) {
			model.addAttribute("notebooks", notebookService.findAll());
			return "viewNotebooks";
		}

		if (notebookForm.getId() != null)
			notebookService.updateWithoutTitle(notebookForm);
		else
			notebookService.save(notebookForm);

		model.addAttribute("notebooks", notebookService.findAll());
		model.addAttribute("notebookForm", new NotebookForm());

		return "viewNotebooks";
	}

}
