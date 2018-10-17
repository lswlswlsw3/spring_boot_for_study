package com.woong;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woong.domain.User;
import com.woong.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm")
	public String lofinForm() {
		System.out.println("/users/loginForm come!");
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String userPassword, HttpSession session) {
		System.out.println("/users/login come!");
		
		User user = userRepository.findByUserId(userId);

		System.out.println("/users/login user : "+user);
		
		if(user == null) {
			System.out.println("Login Fail");
			return "redirect:/users/loginForm";
		}
		
		if(!userPassword.equals(user.getUserPassword())) {
			System.out.println("Login Fail");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("Login sucess");
		session.setAttribute("sessionedUser", user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	
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
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		System.out.println("/{id}/form : "+id);
		
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User) tempUser;
				
		if(id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정할수 있습니다.");
		}
				
		User user = userRepository.findById(id).get();
		System.out.println("id/form의 user : "+user);
		model.addAttribute("user", user); 
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		System.out.println("/{id} : "+id);
		
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User) tempUser;
				
		if(id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정할수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
}
