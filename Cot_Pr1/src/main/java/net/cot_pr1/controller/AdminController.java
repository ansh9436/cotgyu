package net.cot_pr1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.cot_pr1.common.BoardPage;
import net.cot_pr1.domain.QnA;
import net.cot_pr1.domain.User;
import net.cot_pr1.domain.WebBoard;
import net.cot_pr1.domain.WebReply;
import net.cot_pr1.service.AdminService;
import net.cot_pr1.service.FreeBoardService;
import net.cot_pr1.service.NoticeService;
import net.cot_pr1.service.QnAService;
import net.cot_pr1.service.UserService;
import net.cot_pr1.service.WebBoardService;



@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//관리자 비밀번호 확인 창으로 이동
	@RequestMapping(value="adminform")
    public ModelAndView form() throws Exception{
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/admin/adminlogin");
        return mav;
    }
	
	//비밀번호 입력 후 관리자 확인 
	@RequestMapping(value="login", method=RequestMethod.POST)
    public ModelAndView login(@RequestParam String password) throws Exception{
		//관리자 유저 찾기 
		User user = userService.findByID("관리자");
		
		//입력한 비밀번호와 관리자 유저 비밀번호 비교 
		if(passwordEncoder.matches(password ,user.getPassword())){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/list");
			return mav;
		}
		//비밀번호 오류시 창 이동 
		else{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/loginfail");
		return mav;
		}
    }
	
	
	//게시물 모두 보여주기
	@RequestMapping(value="list")
    public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
		
		
		//레코드의 개수
		int count = adminService.countboard(searchOption, keyword);
		//페이지
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		List<WebBoard> list = adminService.Viewlist(start, end, searchOption, keyword);
		
		
		//데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); //list
		map.put("count", count); //레코드 개수
		map.put("searchOption", searchOption); //검색 옵션
		map.put("keyword", keyword); //검색 키워드
		map.put("boardPage", boardPage); 
		
		//모델과 뷰
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // 맵에 저장된 데이터를 mav(모델 앤 뷰)에 저장
    			
		mav.setViewName("/admin/adminmode");
		return mav;
	
    }
	
	//댓글 모두 보여주기
		@RequestMapping(value="replylist")
	    public ModelAndView replylist(@RequestParam(defaultValue="replytext") String searchOption, 
				@RequestParam(defaultValue="") String keyword,
				@RequestParam(defaultValue="1") int curPage) throws Exception{
			
			
			//레코드의 개수
			int countreply = adminService.countreply(searchOption, keyword);
			//페이지
			BoardPage boardPage = new BoardPage(countreply, curPage);
			int start = boardPage.getPageBegin();
			int end = boardPage.getPageEnd();
			List<WebReply> list = adminService.Viewreplylist(start, end, searchOption, keyword);
			
			//데이터를 맵에 저장
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list); //list
			map.put("countreply", countreply); //레코드 개수
			map.put("searchOption", searchOption); //검색 옵션
			map.put("keyword", keyword); //검색 키워드
			map.put("boardPage", boardPage); 
			
			//모델과 뷰
			ModelAndView mav = new ModelAndView();
			mav.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
	    			
			mav.setViewName("/admin/adminmode_reply");
			return mav;
		
	    }
	
		//유저 리스트 
		@RequestMapping(value="userlist")
	    public ModelAndView userlist(@RequestParam(defaultValue="name") String searchOption, 
				@RequestParam(defaultValue="") String keyword,
				@RequestParam(defaultValue="1") int curPage) throws Exception{
			
			
			//레코드의 개수
			int countuser = adminService.countuser(searchOption, keyword);
			//페이지
			BoardPage boardPage = new BoardPage(countuser, curPage);
			int start = boardPage.getPageBegin();
			int end = boardPage.getPageEnd();
			List<User> list = adminService.Viewuserlist(start, end, searchOption, keyword);
			
			//데이터를 맵에 저장
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list); //list
			map.put("countuser", countuser); //레코드 개수
			map.put("searchOption", searchOption); //검색 옵션
			map.put("keyword", keyword); //검색 키워드
			map.put("boardPage", boardPage); 
			
			//모델과 뷰
			ModelAndView mav = new ModelAndView();
			mav.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
	    			
			mav.setViewName("/admin/adminmode_users");
			return mav;
		
	    }
		
		//관리자 회원탈퇴
		@RequestMapping("/userunregister/{username}")
		public ModelAndView userUnregi(@PathVariable("username") String username){
			
			userService.unregister(username);
			
			
			ModelAndView mav = new ModelAndView();
	    			
			mav.setViewName("redirect:/admin/userlist");
			return mav;
		}
	
}
	