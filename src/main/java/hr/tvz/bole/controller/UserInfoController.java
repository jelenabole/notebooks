package hr.tvz.bole.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import hr.tvz.bole.form.UserForm;
import hr.tvz.bole.form.mapper.UserMapper;
import hr.tvz.bole.model.UserRole;
import hr.tvz.bole.service.impl.UserService;

@Controller
@SessionAttributes({ "userRole" })
public class UserInfoController {

	private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	UserService userService;

	@GetMapping("/user/{username}")
	public String getUserInfo(@SessionAttribute UserRole userRole, @PathVariable String username, Model model) {

		// TODO - provjera da li je to taj korisnik:
		if (!userRole.getUsername().equals(username)) {
			logger.info("GET - 403 - info o korisniku: " + username);
			return "redirect:/403";
		}

		UserForm userForm = UserMapper.mapUserToUserForm(userService.findOneByUsername(username));

		logger.info("GET - info o korisniku: " + username);
		// podaci o korisniku, mogućnost promjene informacija!
		// automatski input polja sa svim promjenjivim podacima
		// email, name, surname

		model.addAttribute("userForm", userForm);
		return "userInfo";
	}

	@PostMapping("/user/{username}")
	public String updateUserInfo(@ModelAttribute @Valid UserForm userForm, Model model) {
		userService.updateUser(userForm);
		logger.info("POST - update korisnika: " + userForm.getUsername());

		return "userInfo";
	}

}
