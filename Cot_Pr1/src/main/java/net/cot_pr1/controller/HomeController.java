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
import net.cot_pr1.dao.UserDao;
import net.cot_pr1.dao.WebBoardDao;
import net.cot_pr1.domain.FreeBoard;
import net.cot_pr1.domain.Gallery;
import net.cot_pr1.domain.User;
import net.cot_pr1.domain.WebBoard;



@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private WebBoardDao boardDao;
	@Autowired
	private FreeBoardDao freeboardDao;
	@Autowired
	private GalleryDao galleryDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping("/")
	public ModelAndView Home(HttpSession session) throws Exception{
// �α���ȭ�鿡�� ������.. ���⼭ ���ָ� �ȵ�...	
//		String userId = (String) session.getAttribute("userId");
//		User loginuser = userDao.findByID(userId);	
//		map.put("loginuser", loginuser);
		List<WebBoard> poplist = boardDao.popboard();
		List<FreeBoard> popFlist = freeboardDao.popboard();
		List<Gallery> popImglist = galleryDao.poplist();
		List<WebBoard> recentlist = boardDao.recentboard(); //�ֽ� �� ���
	
		//�����͸� �ʿ� ����
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("poplist", poplist);
		map.put("popFlist", popFlist);
		map.put("popImglist",popImglist);
		map.put("recentlist", recentlist);
	
		//�𵨰� ��
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // �ʿ� ����� �����͸� mav�� ����
        mav.setViewName("home"); // �並 list.jsp�� ����
       
        return mav; // list.jsp�� List�� ���޵ȴ�.
	}
	
	

}