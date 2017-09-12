package net.cot_pr1.security;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.cot_pr1.controller.HomeController;

@Controller
public class SignController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(@RequestParam(value = "error", required = false) String error, Model model) {
		model.addAttribute("error", error);
		String guest_password = passwordEncoder.encodePassword("guest", null);
		String admin_password = passwordEncoder.encodePassword("admin", null);
		logger.info(guest_password + "//" + admin_password);
		return "sign/signin";
	}

	@PreAuthorize("authenticated")
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage(Model model,HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user_name", auth.getName());
		session.setAttribute("userId", auth.getName()); //세션 추가 !
		return "redirect:/";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String denied() {
		return "sign/denied";
	}
	

}
