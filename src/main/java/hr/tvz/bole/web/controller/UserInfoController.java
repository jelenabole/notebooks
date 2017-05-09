package hr.tvz.bole.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.other.mapper.UserMapper;
import hr.tvz.bole.server.service.UserService;
import hr.tvz.bole.web.form.UserForm;

@Controller
@SessionAttributes({ "user", "userRole" })
public class UserInfoController {

	private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	UserService userService;

	@GetMapping("/userInfo")
	public String getUserInfo(@SessionAttribute UserRole userRole, Model model) {
		logger.info("GET - info o korisniku: " + userRole.getUser());
		UserForm userForm = UserMapper.mapUserToUserForm(userService.findOne(userRole.getUser().getId()));

		model.addAttribute("userForm", userForm);
		return "userInfo";
	}

	@PostMapping("/userInfo")
	public String updateUserInfo(@ModelAttribute @Valid UserForm userForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "userInfo";
		}

		userService.update(userForm);
		logger.info("POST - update korisnika: " + userForm.getUsername());

		model.addAttribute("saved", true);
		return "userInfo";
	}
}
