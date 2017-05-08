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

import hr.tvz.bole.exceptions.UserExistsException;
import hr.tvz.bole.form.RegisterForm;
import hr.tvz.bole.form.UserForm;
import hr.tvz.bole.model.User;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/login")
	public String showLogin(Model model, UserForm userForm, HttpServletRequest request) {
		model.addAttribute("userForm", userForm);
		model.addAttribute("error", request.getParameter("error"));

		return "login";
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
		model.addAttribute("user", new RegisterForm());
		
		// TODO - dohvatiti popis usera - provjera (obrisati):
		model.addAttribute("users", userRepository.findAll());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") @Valid RegisterForm user, BindingResult result,
			WebRequest request, Errors errors, Model model) {
		// TODO - da li je webRequest i Errors = potrebno ?!

		User registered = null;

		
		
		// provjeriti greške:
		if (result.hasErrors()) {
			//TODO - skužit kako dohvatiti samo prvu grešku od polja "password"
			System.out.println(result.getErrorCount());
			System.out.println(result.getAllErrors());
			System.out.println(result.getGlobalError());
			return "register";
		}

		// ako nema grešaka, provuć formu, da li user postoji:
		try {
			registered = userRepository.save(mapRegisterFormToUser(user));
		} catch (UserExistsException e) {
			result.rejectValue("username", "register.exception.userExists");
		}
		if (registered == null) {
			// TODO - obrisati grešku (dupla kada username postoji):
			result.rejectValue("username", "register.exception.registerError");
			model.addAttribute("users", userRepository.findAll());
			return "register";
		} else {
			model.addAttribute("userRole", new UserRole(user.getName(), userRepository.hasAdminRole(user.getName())));
			return "redirect:/login";
		}
	}

	// TODO - pass se zapisuje kao pass,pass
	private User mapRegisterFormToUser(RegisterForm user) {
		return new User(null, user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), true);
	}

}
