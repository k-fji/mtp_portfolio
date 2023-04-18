package com.zdrv.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zdrv.app.dao.LoginMapper;

@Controller
public class HomeController {
	@Autowired
	LoginMapper loginMapper;

	@RequestMapping({"/", "/home"})
	public String home(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		return "/home";
	}


	@RequestMapping("/home/sample_mypage")
	public String showMypageSample(Model model) {

		return "sample_mypage";
	}

	@RequestMapping("/home/message")
	public String showMessage() {
		return "message";
	}

	@RequestMapping("/portfolio")
	public String showPortfolio() {
		return "portfolio";
	}

	@RequestMapping("/portfolio_works")
	public String showPortfolioWorks() {
		return "portfolio_works";
	}

	@RequestMapping("/register_done")
	public String showRegister_done() {
		return "register_done";
	}
}
