/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.controller;

import java.util.List;

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
		User currentUser = getCurrentUser(username);
		model.addAttribute("cookieUser", currentUser);
	}
	
	public User getCurrentUser(String username) {
		User result = userRepo.findOneByUsername(username);
		if (result == null) {
			return new User("Guest");
		} else {
			return result;
		}
	}
	
	@GetMapping("/register")
	public String registerUser(Model model) {
		User u = new User();
		model.addAttribute("newUser", u);
		return "register";
	}	
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User u, Model model) {
		if (userRepo.existsByUsername(u.getUsername())) {
			model.addAttribute("error", "Username is already taken.");
			return "register";
		} else if (u.getUsername().length() == 0){
			model.addAttribute("error", "Username cannot be empty.");
			return "register";
		} else if (u.getPassword().length() == 0){
			model.addAttribute("error", "Password cannot be empty.");
			return "register";
		}
		boolean hasSpecial = false;

		for(int counter = 0; counter < u.getPassword().length(); counter++) {
			if(!Character.isDigit(u.getPassword().charAt(counter))
                && !Character.isLetter(u.getPassword().charAt(counter))
                && !Character.isWhitespace(u.getPassword().charAt(counter))) {
				hasSpecial = true;
			}
		}
		if(!hasSpecial) {
			model.addAttribute("error", "Password must have at least one special character.");
			return "register";
		}
		if (userRepo.findAll().isEmpty()) { // If no user accounts exist, the first one will be made an admin
			u.setAdmin(true);
			u.setCanAddEvents(true);
			u.setCanEditEvents(true);
		}
		u.attendEvent(0l); // Adds ID of 0 to events being attended to prevent the arraylist from being null
		userRepo.save(u);
		model.addAttribute("message","Account " + u.getUsername() + " successfully created.");
		return "index";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		User u = new User();
		model.addAttribute("loginUser", u);
		return "login";
	}	
	
	@PostMapping("/login")
	public String login(@ModelAttribute User u, Model model, HttpServletResponse response) {
		User loginUser = userRepo.findOneByUsernameAndPassword(u.getUsername(), u.getPassword());		
		if (loginUser != null) {
			Cookie cookie = new Cookie("username", u.getUsername());
			response.addCookie(cookie);
			model.addAttribute("cookieUser", loginUser);
			model.addAttribute("message","Sucessfully logged in.");
			return "index";
		} else {
			model.addAttribute("error", "Username or password was incorrect.");
			return "login";
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletResponse response) {
		Cookie cookie = new Cookie("username", "Guest");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		model.addAttribute("cookieUser", new User("Guest"));
		model.addAttribute("message","You have been logged out.");
		return "index";
	}
	

}
