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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.RoleForm;

@Controller
@SessionAttributes({ "currentUser", "filterForm" })
public class ViewUsersController {

	private static Logger logger = LoggerFactory.getLogger(ViewUsersController.class);

	@Autowired
	UserService userService;

	@ModelAttribute("filterForm")
	public FilterForm getFilterForm() {
		List<String> orderByList = Arrays.asList("name", "surname", "username", "email", "enabled",
				"roles");
		String objectName = "user";
		return new FilterForm(orderByList, objectName);
	}

	@GetMapping("/viewUsers")
	@Secured({ "ROLE_ADMIN" })
	public String getNewForm(Model model) {
		logger.info("GET - viewUsers");

		// poslati za filtere:
		model.addAttribute("filterForm", getFilterForm());

		// TODO - poslati enum (role)
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roleForm", new RoleForm());
		return "viewUsers";
	}

	@PostMapping("/viewUsers")
	@Secured("ROLE_ADMIN")
	public String filterUsers(@ModelAttribute FilterForm filterForm, Model model) {
		logger.info("GET - view users");

		model.addAttribute("notes", userService.getFilteredNotes(filterForm));

		return "viewNotes";
	}

	@PostMapping("/saveRole")
	@Secured({ "ROLE_ADMIN" })
	public String addNewRole(@Valid RoleForm roleForm, Model model) {
		logger.info("POST - viewUsers - add new Role");

		// TODO - dodati novu rolu u bazu

		model.addAttribute("users", userService.findAll());

		return "viewUsers";
	}

	@GetMapping("/viewUsers/{id}")
	@Secured("ROLE_ADMIN")
	public String deleteUser(Model model, @PathVariable int id) {
		// TODO - nekakvo upozorenje prije nastavka!
		logger.info("GET - deleteUser id: " + id);
		userService.delete(id);
		return "redirect:/viewUsers";
	}

	@GetMapping("/enableUser/{id}")
	@Secured("ROLE_ADMIN")
	public String changeEnableStatus(Model model, @PathVariable int id) {
		logger.info("GET - enable/disable user id: " + id);
		userService.changeEnabledStatus(id);
		return "redirect:/viewUsers";
	}

}
