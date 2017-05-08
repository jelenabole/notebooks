package hr.tvz.bole.controller;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
import hr.tvz.bole.form.LoginForm;
import hr.tvz.bole.form.UserForm;
import hr.tvz.bole.form.mapper.UserMapper;
import hr.tvz.bole.model.User;
import hr.tvz.bole.service.impl.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String showLogin(Model model, @ModelAttribute LoginForm loginForm, HttpServletRequest request) {
		model.addAttribute("userForm", loginForm);
		model.addAttribute("error", request.getParameter("error"));

		return "login";
	}

	@GetMapping("/403")
	public String accessDeninedHanadler(Model model) {
		return "/error/403";
	}

	// Login form with error
	/*
	 * @GetMapping("/login-error") public String loginError(Model model,
	 * UserForm userForm) { model.addAttribute("userForm", userForm);
	 * model.addAttribute("error", true); return "login"; }
	 */
	// XXX - nije potrebno kreirati POST metodu
	// defaultna Spring Security implementacija obrađuje login

	@GetMapping("/register")
	public String showRegistrationForm(WebRequest request, Model model) {
		model.addAttribute("userForm", new UserForm());

		// TODO - dohvatiti popis usera - provjera (obrisati):
		model.addAttribute("users", userService.findAllUsers());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid UserForm userForm, BindingResult result, WebRequest request, Errors errors,
			Model model) {
		// TODO - da li je webRequest i Errors = potrebno ?!

		if (userService.checkIfUserExists(userForm.getUsername())) {
			result.rejectValue("username", "register.exception.userExists");
		}
		// provjeriti greške:
		if (result.hasErrors()) {
			// TODO - prikazati samo jednu grešku na polju email:
			if (result.getFieldErrorCount("email") == 2) {
				System.out.println("prikaz obje greške - polje email");
			}
			model.addAttribute("users", userService.findAllUsers());
			return "register";
		}

		// ako nema grešaka, provuć formu, da li user postoji:
		User registered = null;
		try {
			registered = userService.saveUser(UserMapper.mapUserFormToUser(userForm));
		} catch (UserExistsException e) {
			// TODO - pregledat greške:
			System.out.println("greška - user postoji");
			result.rejectValue("username", "register.exception.userExists");
		} catch (RoleExistsForUser e) {
			result.rejectValue("username", "register.exception.roleExists");
		}

		if (registered == null) {
			// TODO - obrisati grešku (nikada se ne bi trebala prikazati):
			// result.reject("user", "register.exception.registerError");
			model.addAttribute("users", userService.findAllUsers());
			return "register";
		} else {
			return "redirect:/login";
		}
	}

}
