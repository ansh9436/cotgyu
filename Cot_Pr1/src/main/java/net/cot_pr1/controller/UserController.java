package net.cot_pr1.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import net.cot_pr1.domain.Authenticate;
import net.cot_pr1.domain.User;
import net.cot_pr1.service.UserService;


@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="uploadPath2")
	String uploadPath2;

	@Autowired
	UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/form")
	public String createform(Model model){
		model.addAttribute("user", new User());
		return "users/form";
	}
	
	@RequestMapping(value="/create" , method=RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult){
		log.debug("User : {}", user);
		if(bindingResult.hasErrors()){
			System.out.println("��ȿ���˻�");
			
			log.debug("Binding Result has error");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				log.debug("error: {}", error.getDefaultMessage());
			}
			return "users/form";
		}		
		userService.create(user);
		log.debug("Database: {}", userService.findByID(user.getUserId()));
		return "users/joinok";
	}
	//������- �������� ���̵� �����ͼ� ���� �ҷ����� 
	@RequestMapping("/myinfo")
	public String myifo(Model model, HttpSession session){
		if(session ==null){
			throw new IllegalArgumentException("����� ���̵� �ʿ��մϴ�.");
		}
		
		String userId = (String) session.getAttribute("userId");
		User user = userService.findByID(userId);		
		model.addAttribute("user", user);
		return "users/myinfo";
	}
	
	//���� ����
	@RequestMapping("/modify")
	public String modify(Model model, HttpSession session){
		if(session ==null){
			throw new IllegalArgumentException("����� ���̵� �ʿ��մϴ�.");
		}
		String userId = (String) session.getAttribute("userId");
		User user = userService.findByID(userId);		
		model.addAttribute("user", user);
		return "users/infomodify";
	}
	
	//�̹��� �ø���â���� �̵� 
		@RequestMapping(value="imgmodify", method=RequestMethod.POST)
		public ModelAndView imgmodify(@ModelAttribute User vo, HttpSession session, MultipartFile file, ModelAndView mav) throws Exception{
			String userId = (String) session.getAttribute("userId");
		    //�����̸� ���̱�
		    UUID uuid = UUID.randomUUID();	    
	        String savedName = uuid.toString()+"_"+file.getOriginalFilename();
	        File target = new File(uploadPath2, savedName);
	        
	        // �ӽõ��丮�� ����� ���ε�� ������ ������ ���丮�� ����
	        // FileCopyUtils.copy(����Ʈ�迭, ���ϰ�ü)
	        FileCopyUtils.copy(file.getBytes(), target);

	   
	        mav.addObject("savedName", savedName);
	        //vo�� ������ �ֱ�
	        
	        vo.setProfileimg(savedName);
	        vo.setUserId(userId);
	     
	        userService.imgmodify(vo);
	        
	        mav.setViewName("redirect:/");
	        return mav;

		}
	
	
	
	@RequestMapping(value="" , method=RequestMethod.POST)
	public String updatecreate(@Valid User user, BindingResult bindingResult, HttpSession session){
		log.debug("User : {}", user);
		if(bindingResult.hasErrors()){
			System.out.println("��ȿ���˻�");
			
			log.debug("Binding Result has error");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				log.debug("error: {}", error.getDefaultMessage());
			}
			return "users/form";
		}
		
		Object temp = session.getAttribute("userId");
		if(temp==null){
			throw new NullPointerException();
		}		
	
		userService.update(user);
		log.debug("Database: {}", userService.findByID(user.getUserId()));
		return "redirect:/";
	}
	
	@RequestMapping("/login/form")
	public String loginform(Model model){
		model.addAttribute("authenticate", new Authenticate());
		return "users/login";
	}
	
	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult, HttpSession session, Model model){
		if(bindingResult.hasErrors()){
			return "users/login";
		}
		
		User user = userService.findByID(authenticate.getUserId());
		
		if(user ==null){
			model.addAttribute("errorMessage", "�������� �ʴ� ������Դϴ�.");
			return "users/login";
		}
		
		if(!user.matchPassword(authenticate)){
			model.addAttribute("errorMessage", "��й�ȣ�� Ʋ���ϴ�.");
			return "users/login";
		}
		session.setAttribute("userId", user.getUserId());
		session.setAttribute("profileimg", user.getProfileimg());
		// ���� ����� ���� ���� 
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("userId");
		return "redirect:/";
	}
	
	
	//���̵� �ߺ�üũ 

	 @RequestMapping(value = "/checkId.do")
	 public void checkId(HttpServletRequest req, HttpServletResponse res,
	   ModelMap model) throws Exception {
	  PrintWriter out = res.getWriter();
	  try {

	   // �Ѿ�� ID�� �޴´�.
	   String paramId = (req.getParameter("prmId") == null) ? "" : String
	     .valueOf(req.getParameter("prmId"));

	   User vo = new User();
	   vo.setUserId(paramId.trim());
	   int chkPoint = userService.checkId(vo);

	   out.print(chkPoint);
	   out.flush();
	   out.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	   out.print("1");
	  }
	 }
//security �׽�Ʈ
	 @PreAuthorize("authenticated")
		@RequestMapping(value = "/mypage", method = RequestMethod.GET)
		public String mypage(Model model,HttpSession session) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.getName() ==null){
				return "redirect:/users/form";
			}
			//model.addAttribute("user_name", auth.getName());
			session.setAttribute("userId", auth.getName()); //���� �߰� !
			return "redirect:/";
		}
}


