/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dmacc.model.User;
import dmacc.repository.UserRepository;

@Controller
public class WebController {
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
	
	@RequestMapping({"/", "/index"})
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/admin-home")
	public String adminHome(Model model) {
		return "/admin/home";
	}
	
	@GetMapping("/admin-viewUsers")
	public String adminViewUsers(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "/admin/userlist";
	}
	
	@GetMapping("/user-edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		model.addAttribute("userToEdit", u);
		return "/admin/edituser";
	}
	
	@PostMapping("/user-update/{id}")
	public String updateUser(User u, Model model) {
		if (userRepo.existsByUsername(u.getUsername())) { // Checks if username already exists
			if (userRepo.findOneByUsername(u.getUsername()).getId() != u.getId()) {// If that username is not the same id, return an error. Otherwise, it's the edited user
				model.addAttribute("error", "User with that name already exists");
				return adminViewUsers(model);
			}
		} 
		if (u.getUsername().equals("")){
			model.addAttribute("error", "Username cannot be blank");
			return adminViewUsers(model);
		}
		userRepo.save(u);
		model.addAttribute("message", "User successfully edited");
		return adminViewUsers(model);
	}
	
	@GetMapping("/user-delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		userRepo.delete(u);
		return adminViewUsers(model);
	}
	
	
}