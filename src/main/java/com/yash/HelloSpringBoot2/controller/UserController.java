package com.yash.HelloSpringBoot2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.yash.HelloSpringBoot2.dao.UserDAOImpl;
import com.yash.HelloSpringBoot2.model.User;

@Controller
public class UserController {

	UserDAOImpl daoImpl = new UserDAOImpl();

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("user", new User());
		System.out.println("Enter");
		return "index";
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/welcome")
	public String showWelcomePage(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("username", user.getUsername());
		model.addAttribute("firstname", user.getFirstname());
		model.addAttribute("email", user.getEmail());
		return "welcome";
	}

	@PostMapping("/processRegister")
	public String processRegister(@ModelAttribute User user, BindingResult errors, Model model,
			HttpServletRequest request) {

		daoImpl.insertUser(user);

		HttpSession session = request.getSession(true);
		
		session.setAttribute("user", user);
		return "welcome";
	}

	@GetMapping("/processLogin")
	public String processLogin(@ModelAttribute User user, BindingResult errors, Model model,
			HttpServletRequest request) {
		if(daoImpl.loginValidate(user) == null) {
			return "index";
		}
		
		User loggedUser;
		loggedUser = daoImpl.loginValidate(user);
		HttpSession session = request.getSession(true);
		session.setAttribute("user", loggedUser);
		model.addAttribute(loggedUser);
		return "welcome";
	}
}
