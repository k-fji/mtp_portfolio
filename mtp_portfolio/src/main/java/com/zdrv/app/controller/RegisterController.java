package com.zdrv.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zdrv.app.domain.FormUser;
import com.zdrv.app.domain.User;
import com.zdrv.app.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserService userSrevice;

	@GetMapping("/register")
	public String registerGet(Model model) {
		model.addAttribute("formUser", new FormUser());
		return "register";
	}


	@PostMapping("/register")
	public String registerPost(@Valid FormUser formUser, Errors errors, Model model) {

			if(errors.hasErrors()) {

				return "register";
			}
			else {
				try {

					User user = new User();
					user.setUserId(formUser.getInputUserId());
					user.setPass(formUser.getInputPass());
					userSrevice.registerUser(user);

				}catch (Exception e) {

					boolean errorFlag = true;
					model.addAttribute("errorFlag", errorFlag);

				return "register";
			}
			return "register_done";
		}
	}

	@RequestMapping("/registerDone")
	public String registerDone() {
		return "register_done";
	}

}
