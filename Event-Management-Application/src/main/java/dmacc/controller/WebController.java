/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@ModelAttribute
	public void getCookies(@CookieValue(value = "username", defaultValue = "Guest") String username, Model model) {
	    model.addAttribute("cookieUsername", username);
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}
}
