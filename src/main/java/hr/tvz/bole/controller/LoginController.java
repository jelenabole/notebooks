package hr.tvz.bole.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.tvz.bole.form.UserForm;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLogin(Model model, UserForm userForm) {
		model.addAttribute("userForm", userForm);
		model.addAttribute("error", false);
		// ako nije nađen, ili userform ima probleme - staviti graške:

		return "login";
	}

	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model, UserForm userForm) {
		model.addAttribute("userForm", userForm);
		model.addAttribute("error", true);
		return "login";
	}

	// TODO - nije potrebno kreirati POST metodu
	// defaultna Spring Security implementacija obrađuje login
}
