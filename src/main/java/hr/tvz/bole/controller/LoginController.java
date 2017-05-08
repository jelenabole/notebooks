package hr.tvz.bole.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hr.tvz.bole.form.UserForm;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLogin(Model model, UserForm userForm, HttpServletRequest request) {
		model.addAttribute("userForm", userForm);
		model.addAttribute("error", request.getParameter("error"));

		return "login";
	}

	// Login form with error
	/*
	@GetMapping("/login-error")
	public String loginError(Model model, UserForm userForm) {
		model.addAttribute("userForm", userForm);
		model.addAttribute("error", true);
		return "login";
	}
	*/
	// XXX - nije potrebno kreirati POST metodu
	// defaultna Spring Security implementacija obrađuje login
}
