/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dmacc.model.Event;
import dmacc.model.User;
import dmacc.repository.EventRepository;
import dmacc.repository.UserRepository;

@Controller
public class WebController {
	@Autowired
	UserRepository userRepo;

	@Autowired
	EventRepository eventRepo;

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
		User oldUser = userRepo.getById(u.getId());
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
		u.setAttendingEvents(oldUser.getAttendingEvents());
		u.setPassword(oldUser.getPassword());
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
	
	@GetMapping("/user-reset-password/{id}")
	public String editUserPasswordUser(@PathVariable("id") long id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		model.addAttribute("userToEdit", u);
		return "/reset-password";
	}
	
	@GetMapping("/admin-reset-password/{id}")
	public String editUserPasswordAdmin(@PathVariable("id") long id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		model.addAttribute("userToEdit", u);
		return "/admin/reset-password";
	}
	
	@PostMapping("/admin-update-user-password/{id}")
	public String updateUserPasswordAdmin(User u, Model model) {
		User oldUser = userRepo.findById(u.getId()).orElse(null);
		oldUser.setPassword(u.getPassword());
		userRepo.save(oldUser);
		model.addAttribute("message","Password for " + oldUser.getUsername() + " has been reset");
		return adminViewUsers(model);
	}

	@PostMapping("/user-update-user-password/{id}")
	public String updateUserPasswordUser(User u, Model model) {
		User oldUser = userRepo.findById(u.getId()).orElse(null);
		oldUser.setPassword(u.getPassword());
		userRepo.save(oldUser);
		model.addAttribute("message","Your password has been changed");
		return showUserProfile(oldUser.getUsername(), model);
	}
	
	@GetMapping({"/viewAll"})
	public String viewAllEvents(Model model) {
		model.addAttribute("events", eventRepo.findEventByDateAfterOrderByDateAsc(Date.valueOf(LocalDate.now().minusDays(1))));
		model.addAttribute("types", eventRepo.findTypes());
		return "all-events";
	}

	@GetMapping({"/viewAll/{type}"})
	public String viewAllEventsByType(@PathVariable("type") String type, Model model) {
		if(eventRepo.findAll().isEmpty()) {
			return "all-events";
		}

		if (type.equals("All Events")) {
			return viewAllEvents(model);
		}

		model.addAttribute("events", eventRepo.findEventByTypeOrderByDateAsc(type));
		model.addAttribute("types", eventRepo.findTypes());
		return "all-events";
	}
	
	@GetMapping({"/viewAllPast"})
	public String viewAllPastEvents(Model model) {
		model.addAttribute("events", eventRepo.findAll(Sort.by(Sort.Direction.ASC, "date")));
		model.addAttribute("types", eventRepo.findTypes());
		return "all-events";
	}

	@GetMapping({"/viewEvent/{id}"})
	public String viewEventPage(@PathVariable("id") long id, Model model) {
		Event e = eventRepo.findById(id).orElse(null);
		model.addAttribute("eventDetails", e);
		return "event";
	}

	@GetMapping({"/registerEvent/{id}"})
	public String registerForEvent(@CookieValue(value = "username", defaultValue = "Guest") String username, @PathVariable("id") long id, Model model) {
		User currentUser = getCurrentUser(username);
		currentUser.attendEvent(id);
		userRepo.save(currentUser);
		return viewAllEvents(model);
	}

	@GetMapping({"/unregisterEvent/{id}"})
	public String unregisterForEvent(@CookieValue(value = "username", defaultValue = "Guest") String username, @PathVariable("id") long id, Model model) {
		User currentUser = getCurrentUser(username);
		currentUser.unattendEvent(id);
		userRepo.save(currentUser);
		return viewAllEvents(model);
	}

	@GetMapping("/inputEvent")
	public String addNewEvent(Model model) {
		Event e = new Event();
		model.addAttribute("newEvent", e);
		return "add-event";
	}

	@PostMapping("/inputEvent")
	public String addEvent(@ModelAttribute Event e, Model m) {
		eventRepo.save(e);
		return viewAllEvents(m);
	}

	@PostMapping("/update/{id}")
	public String reviseEvent(Event e, Model model) {
		if (eventRepo.existsById(e.getId())) { 
			e.setAttendeeInfo(eventRepo.getById(e.getId()).getAttendeeInfo()); // Keeps the attendeeInfo, as it's not in the update form
		}
		eventRepo.save(e);
		return viewAllEvents(model);
	}

	@GetMapping("/edit/{id}")
	public String showUpdateEvent(@PathVariable("id") long id, Model model) {
		Event e = eventRepo.findById(id).orElse(null);
		model.addAttribute("newEvent", e);
		return "add-event";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEvent(@PathVariable("id") long id, Model model) {
		Event e = eventRepo.findById(id).orElse(null);
		eventRepo.delete(e);
		return viewAllEvents(model); 
	}
	
	@GetMapping({"/editEventAttendeeInfo/{id}"})
 	public String editEventAttendeeInfo(@PathVariable("id") long id, Model model) {
 		Event e = eventRepo.getById(id);
 		model.addAttribute("updateEvent", e);
 		return "edit-event-attendee-info";
 	}

 	@PostMapping("/updateEventAttendeeInfo/{id}")
 	public String updateEventAttendeeInfo(Event e, Model model) {
 		Event eToUpdate = eventRepo.getById(e.getId());
 		eToUpdate.setAttendeeInfo(e.getAttendeeInfo());
 		eventRepo.save(eToUpdate);
 		return viewAllEvents(model);
 	}
 	
	@GetMapping("/userProfile")
	public String showUserProfile(@CookieValue(value = "username", defaultValue = "Guest") String username, Model model) {
		User currentUser = getCurrentUser(username);
		ArrayList<Event> userEventsAttending = new ArrayList<Event>();
		for (int i = 0; i < currentUser.getAttendingEvents().size(); i++) {
			if (currentUser.getAttendingEvents().get(i) != 0) {
				Event e = eventRepo.findById(currentUser.getAttendingEvents().get(i)).orElse(null);
				if (e != null) {
					userEventsAttending.add(e);
				}
			}
		}
		model.addAttribute("eventsAttending", userEventsAttending);
		return "user-profile";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		return "/about";
	}
	
	@InitBinder
	protected void dateBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@InitBinder
	protected void timeBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Time.class, new TimeEditor());
	}
}
