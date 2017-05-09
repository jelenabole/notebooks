package hr.tvz.bole.web.controller;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.model.User;
import hr.tvz.bole.other.mapper.UserMapper;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.LoginForm;
import hr.tvz.bole.web.form.UserForm;

@Controller
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String showLogin(Model model, @ModelAttribute LoginForm loginForm,
			HttpServletRequest request) {
		logger.info("GET - login");
		model.addAttribute("userForm", loginForm);
		model.addAttribute("error", request.getParameter("error"));

		return "login";
	}

	@GetMapping("/403")
	public String accessDeninedHanadler(Model model) {
		return "/error/403";
	}

	@GetMapping("/register")
	public String showRegistrationForm(WebRequest request, Model model) {
		logger.info("GET - register");
		model.addAttribute("userForm", new UserForm());

		// TODO - dohvatiti popis usera - provjera (obrisati):
		// model.addAttribute("users", userService.findAll());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid UserForm userForm, BindingResult result, WebRequest request,
			Errors errors, Model model) {
		// TODO - da li je webRequest i Errors = potrebno ?!
		logger.info("POST - register");

		if (userService.checkIfUserExists(userForm.getUsername())) {
			result.rejectValue("username", "register.exception.userExists");
		}
		// provjeriti greske:
		if (result.hasErrors()) {
			logger.info("POST - register - errors: " + result.getErrorCount());
			model.addAttribute("users", userService.findAll());
			return "register";
		}

		// ako nema gresaka, provuc formu, da li user postoji:
		User registered = null;
		try {
			registered = userService.save(UserMapper.mapUserFormToUser(userForm));
		} catch (UserExistsException e) {
			// TODO - greska nije potrebna (provjereno iznad):
			logger.info("POST - register - error - username exists");
			result.rejectValue("username", "register.exception.userExists");
		} catch (RoleExistsForUser e) {
			// TODO - greska nije potrebna
			result.rejectValue("username", "register.exception.roleExists");
		}

		if (registered == null) {
			// TODO - obrisati gresku (nikada se ne bi trebala prikazati):
			// result.reject("user", "register.exception.registerError");
			model.addAttribute("users", userService.findAll());
			return "register";
		} else {
			logger.info("POST - register user: " + userForm.getUsername());
			return "redirect:/login";
		}
	}

}
