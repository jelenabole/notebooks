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
import org.springframework.web.bind.annotation.RequestBody;
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
	public String getNotebooks(Model model) {
		logger.info("GET - view notebooks");

		// create filter form:
		model.addAttribute("filterForm", getFilterForm());

		return "viewNotebooks";
	}

	/**** OSTALE METODE VRAĆAJU FRAGMENTE STRANICE ****/

	// XXX - AJAX - edit:
	@GetMapping("/notebook/edit/{id}")
	@Secured("ROLE_ADMIN")
	public String loadNotebook(@PathVariable Integer id, Model model) {
		logger.info("EDIT - view notebooks");

		model.addAttribute("notebookForm", notebookService.getOneAsForm(id));

		return "fragments/forms :: notebookForm";
	}

	// XXX - AJAX - new:
	@GetMapping("/notebook/new")
	@Secured("ROLE_ADMIN")
	public String createNotebook(Model model) {
		logger.info("NEW - view notebooks");
		model.addAttribute("notebookForm", new NotebookForm());

		return "fragments/forms :: notebookForm";
	}

	// XXX - AJAX - remove form (cancel):
	@GetMapping("/notebook/removeForm")
	@Secured("ROLE_ADMIN")
	public String removeForm(Model model) {
		logger.info("REMOVE FORM - view notebooks");
		model.addAttribute("notebookForm", new NotebookForm());

		return "fragments/forms :: empty";
	}

	// XXX - AJAX - save:
	@PostMapping("/notebook/save")
	@Secured("ROLE_ADMIN")
	public String saveNotebook(@Valid @RequestBody NotebookForm notebookForm, BindingResult result,
			Model model) {
		logger.info("VALIDATE - view notebooks");

		if (result.hasErrors()) {
			model.addAttribute("notebookForm", notebookForm);
			return "fragments/forms :: notebookForm";
		}

		logger.info("SAVE - view notebooks");

		notebookService.save(notebookForm);
		return "fragments/forms :: empty";
	}

	// XXX - AJAX - filter/sort:
	@PostMapping("/notebooks/search")
	public String searchNotes(@RequestBody FilterForm filterForm, Model model) {
		logger.info("GET/POST - search notebooks");

		model.addAttribute("notebooks", notebookService.getFilteredNotebooks(filterForm));

		return "fragments/tables :: viewNotebooksTable";
	}

}