package net.cot_pr1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.cot_pr1.dao.WebBoardDao;
import net.cot_pr1.domain.WebBoard;



@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private WebBoardDao boardDao;
	
//	@Autowired
//	private FMyBoardDao fboardDao;
//	@Autowired
//	MyGalleryDao galleryDao;

	@RequestMapping("/")
	public ModelAndView list() throws Exception{
		
		
		List<WebBoard> poplist = boardDao.popboard();
		//List<FBoard> popFlist = fboardDao.popboard();
	//	List<gallery> popImglist = galleryDao.poplist();
		//�����͸� �ʿ� ����
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("poplist", poplist);
	//	map.put("popFlist", popFlist);
	//	map.put("popImglist",popImglist);
		
		//�𵨰� ��
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // �ʿ� ����� �����͸� mav�� ����
        mav.setViewName("home"); // �並 list.jsp�� ����
       
        return mav; // list.jsp�� List�� ���޵ȴ�.
	}
	
	

}