/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dmacc.model.User;
import dmacc.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@ModelAttribute
	public void getCookies(@CookieValue(value = "username", defaultValue = "Guest") String username, Model model) {
	    model.addAttribute("cookieUsername", username);
	}
	
	@GetMapping("/register")
	public String registerUser(Model model) {
		User u = new User();
		model.addAttribute("newUser", u);
		return "register";
	}	
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User u, Model model) {
		if (userRepo.findByUsername(u.getUsername()).isEmpty()) {
			userRepo.save(u);
			return "index";
		} else {
			model.addAttribute("error", "Username is already taken.");
			return "register";
		}
		
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		User u = new User();
		model.addAttribute("loginUser", u);
		return "login";
	}	
	
	@PostMapping("/login")
	public String login(@ModelAttribute User u, Model model, HttpServletResponse response) {
		if (!userRepo.findByUsernameAndPassword(u.getUsername(), u.getPassword()).isEmpty()) {
			Cookie cookie = new Cookie("username", u.getUsername());
			response.addCookie(cookie);
			return "index";
		} else {
			model.addAttribute("error", "Username or password was incorrect.");
			return "login";
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletResponse response) {
		Cookie cookie = new Cookie("username", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "index";
	}
}
