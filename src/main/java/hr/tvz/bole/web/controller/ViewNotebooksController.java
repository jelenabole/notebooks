package hr.tvz.bole.web.controller;

import java.util.Arrays;
import java.util.List;

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
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.NotebookForm;

@Controller
@SessionAttributes({ "currentUser", "filterForm" })
public class ViewNotebooksController {

	private static Logger logger = LoggerFactory.getLogger(ViewNotebooksController.class);

	@Autowired
	NotebookService notebookService;

	@ModelAttribute("filterForm")
	public FilterForm getFilterForm() {
		List<String> orderByList = Arrays.asList("title", "description", "numberOfNotes");
		String objectName = "notebook";

		return new FilterForm(orderByList, objectName);
	}

	@GetMapping("/viewNotebooks")
	@Secured("ROLE_ADMIN")
	public String getNewForm(@ModelAttribute NotebookForm notebookForm, Model model) {
		logger.info("GET - view notebooks");

		// poslati za filtere:
		model.addAttribute("filterForm", getFilterForm());
		model.addAttribute("notebooks", notebookService.findAllWithNumberOfNotes());

		return "viewNotebooks";
	}

	@PostMapping("/viewNotebooks")
	@Secured("ROLE_ADMIN")
	public String filterNotebooks(@ModelAttribute NotebookForm notebookForm,
			@ModelAttribute FilterForm filterForm, Model model) {
		logger.info("GET - view notebooks");

		model.addAttribute("notebooks", notebookService.getFilteredNotebooks(filterForm));

		return "viewNotebooks";
	}

	@PostMapping("/saveNotebook")
	@Secured("ROLE_ADMIN")
	public String editExistingNotebook(@Valid NotebookForm notebookForm, BindingResult result,
			Model model) {
		logger.info("EDIT/NEW - notebook id: " + notebookForm.getId());

		if (result.hasErrors()) {
			model.addAttribute("notebooks", notebookService.findAllWithNumberOfNotes());
			return "viewNotebooks";
		}
		notebookService.save(notebookForm);

		model.addAttribute("notebookForm", new NotebookForm());

		return "redirect:/viewNotebooks";
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
