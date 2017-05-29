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

import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.other.mapper.UserMapper;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.FilterForm;
import hr.tvz.bole.web.form.UserForm;

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
	public String getUsers(Model model) {
		logger.info("GET - view users");

		// filter form:
		model.addAttribute("filterForm", getFilterForm());

		return "viewUsers";
	}

	/**** OSTALE METODE VRAĆAJU FRAGMENTE STRANICE ****/

	// XXX - AJAX - edit:
	@GetMapping("/user/edit/{id}")
	@Secured("ROLE_ADMIN")
	public String loadUser(@PathVariable Integer id, Model model) {
		logger.info("EDIT - view users");

		// TODO - getOneAsForm - mapiranje staviti u servis:
		model.addAttribute("userForm", UserMapper.mapUserToUserForm(userService.findOne(id)));

		return "fragments/forms :: userForm";
	}

	// XXX - AJAX - new:
	@GetMapping("/user/new")
	@Secured("ROLE_ADMIN")
	public String createUser(Model model) {
		logger.info("NEW - view users");
		model.addAttribute("userForm", new UserForm());

		return "fragments/forms :: userForm";
	}

	// XXX - AJAX - remove form (cancel):
	@GetMapping("/user/removeForm")
	@Secured("ROLE_ADMIN")
	public String removeForm(Model model) {
		logger.info("REMOVE FORM - view users");
		model.addAttribute("userForm", new UserForm());

		return "fragments/forms :: empty";
	}

	// XXX - AJAX - save:
	@PostMapping("/user/save")
	@Secured("ROLE_ADMIN")
	public String saveUser(@Valid @RequestBody UserForm userForm, BindingResult result, Model model)
			throws RoleExistsForUser, UserExistsException {
		logger.info("VALIDATE - view users");

		// TODO - ispisati grešku u formi:
		if (userForm.getRoles().size() == 0) {
			result.rejectValue("roles", "register.exception.minOneRole");
		}

		// check if pass is blank, and username exists:
		if (userForm.getId() == null) {
			if (userForm.getNewPassword().isEmpty())
				result.rejectValue("newPassword", "NotBlank");
			if (userService.checkIfUserExists(userForm.getUsername())) {
				result.rejectValue("username", "register.exception.userExists");
			}
		}

		if (result.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "fragments/forms :: userForm";
		}

		// TODO - ubaciti mapper u servis:
		if (userForm.getId() != null) {
			logger.info("UPDATE - view users");
			userService.update(userForm);
		} else {
			logger.info("SAVE - view users");
			userService.save(userForm);
		}

		return "fragments/forms :: empty";
	}

	// XXX - AJAX - filter/sort:
	@PostMapping("/users/search")
	@Secured("ROLE_ADMIN")
	public String searchNotes(@RequestBody FilterForm filterForm, Model model) {
		logger.info("GET/POST - search users");

		model.addAttribute("users", userService.getFilteredUsers(filterForm));

		return "fragments/tables :: viewUsersTable";
	}

}
