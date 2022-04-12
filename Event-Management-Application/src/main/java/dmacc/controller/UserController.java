/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.model.User;
import dmacc.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	
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
		}
		
		
		
		return "index";
	}
	
	
}
