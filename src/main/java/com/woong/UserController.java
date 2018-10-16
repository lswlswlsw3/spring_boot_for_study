package com.woong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woong.domain.User;
import com.woong.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("user : "+user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		System.out.println("list come");
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable long id, Model model) {
		User user = userRepository.findById(id).get();
		System.out.println("id/form의 user : "+user);
		model.addAttribute("user", user); 
		return "/user/updateForm";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable long id, User newUser) {
		User user = userRepository.findById(id).get();
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
}
