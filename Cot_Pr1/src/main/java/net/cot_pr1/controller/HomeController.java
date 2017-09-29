package net.cot_pr1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.cot_pr1.dao.FreeBoardDao;
import net.cot_pr1.dao.GalleryDao;
import net.cot_pr1.dao.QnADao;
import net.cot_pr1.dao.UserDao;
import net.cot_pr1.dao.WebBoardDao;
import net.cot_pr1.domain.FreeBoard;
import net.cot_pr1.domain.Gallery;
import net.cot_pr1.domain.QnA;

import net.cot_pr1.domain.WebBoard;



@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private WebBoardDao webboardDao;
	@Autowired
	private FreeBoardDao freeboardDao;
	@Autowired
	private GalleryDao galleryDao;
	@Autowired
	private QnADao qnaDao;
	@Autowired
	private UserDao userDao;

	//메인화면
	@RequestMapping("/")
	public ModelAndView Home(HttpSession session) throws Exception{
		//인기게시판 가져오기
		List<WebBoard> poplist = webboardDao.popboard();
		List<FreeBoard> popFlist = freeboardDao.popboard();
		List<Gallery> popImglist = galleryDao.poplist();
		List<QnA> popQnalist = qnaDao.popboard();
		//최신 글 가져오기
		List<WebBoard> recentlist = webboardDao.recentboard(); 
	
		//데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("poplist", poplist);
		map.put("popFlist", popFlist);
		map.put("popImglist",popImglist);
		map.put("popQnalist",popQnalist);
		map.put("recentlist", recentlist);
	
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); 
        mav.setViewName("home"); 
       
        return mav; 
	}
	
	

}