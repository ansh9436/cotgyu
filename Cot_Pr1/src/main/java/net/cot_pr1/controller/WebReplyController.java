package net.cot_pr1.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.cot_pr1.domain.WebReply;
import net.cot_pr1.service.WebReplyService;


@RestController
@RequestMapping("/webreply")
public class WebReplyController {
    
	@Autowired
	WebReplyService webReplyService;
    
    // 댓글 입력
    @RequestMapping(value="insert.do", method=RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute WebReply vo, HttpSession session, @RequestParam int bnum, @RequestParam String replytext){
    	
    	ModelAndView mav = new ModelAndView();
    	
        String userId = (String) session.getAttribute("userId");
   
        
        vo.setReplyer(userId);
        webReplyService.create(vo);
        int rnum = vo.getRnum();
        vo.setRegroup(rnum);
        webReplyService.create_setgroup(vo);
        
        bnum = vo.getBnum();
        
        mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/webboard/view?bnum={bnum}");
        
        return mav;
  
    }
      
    // 댓글 목록(@Controller방식 : veiw(화면)를 리턴)
    @RequestMapping("list.do")
    public ModelAndView list(@RequestParam int bnum, ModelAndView mav){
        List<WebReply> list = webReplyService.list(bnum);
        // 뷰이름 지정
        mav.setViewName("webboard/replylist");	
        // 뷰에 전달할 데이터 지정
        mav.addObject("bnum", bnum);
        mav.addObject("list", list);
        // replyList.jsp로 포워딩
        return mav;
    }
      
    // 댓글 목록(@RestController Json방식으로 처리 : 데이터를 리턴)
    @RequestMapping("listJson.do")
    @ResponseBody // 리턴데이터를 json으로 변환(생략가능)
    public List<WebReply> listJson(@RequestParam int bnum){
        List<WebReply> list = webReplyService.list(bnum);
        return list;
    }
    
    @RequestMapping("delete")
    public ModelAndView replydelete(@RequestParam int rnum, @RequestParam int bnum) throws Exception{
    	webReplyService.delete(rnum);
    	
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/webboard/view?bnum={bnum}");
    	
    	return mav;
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    public ModelAndView replyupdate(@ModelAttribute WebReply vo,  @RequestParam int bnum, @RequestParam int rnum, @RequestParam String replytext) throws Exception{
    	ModelAndView mav = new ModelAndView();
    	
    	webReplyService.update(vo);
    	
    	mav.addObject("bnum", bnum);
    	mav.setViewName("redirect:/webboard/view?bnum={bnum}");
    		
    	return mav; 	
    }
//댓글 수정창으로 연결     
    @RequestMapping(value="/detail/{rnum}", method=RequestMethod.GET)
    public ModelAndView replyDetail(@PathVariable("rnum") Integer rnum, ModelAndView mav){
        WebReply vo = webReplyService.detail(rnum);
        // 뷰이름 지정
        mav.setViewName("webboard/replymodify");
        // 뷰에 전달할 데이터 지정
        mav.addObject("vo", vo);
        // replyDetail.jsp로 포워딩
        return mav;
    }
  
  //댓글 수정창으로 연결     
    @RequestMapping(value="/commentwrite/{rnum}", method=RequestMethod.GET)
    public ModelAndView commentwrite(@PathVariable("rnum") Integer rnum, ModelAndView mav){
        WebReply vo = webReplyService.detail(rnum);
        // 뷰이름 지정
        mav.setViewName("webboard/replycomment");
        // 뷰에 전달할 데이터 지정
        mav.addObject("vo", vo);
        // replyDetail.jsp로 포워딩
        return mav;
    }    
    
    @RequestMapping(value="comment")
    public ModelAndView replycomment(@ModelAttribute WebReply vo, HttpSession session, @RequestParam int bnum, 
    		@RequestParam String replytext, @RequestParam int regroup, @RequestParam int restep, @RequestParam int reindent){
    	ModelAndView mav = new ModelAndView();
    	vo.setRegroup(regroup);
    	vo.setRestep(restep);
    	vo.setReindent(reindent);
    	webReplyService.stepshape(vo); 
    	
    	String userId = (String)session.getAttribute("userId");
    	vo.setReplyer(userId);
    	
    	webReplyService.createcomment(vo); //코멘트생성
    	//실행하고 보여줄 페이지
    	bnum = vo.getBnum();        
        mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/webboard/view?bnum={bnum}");
        
    	return mav;
    }
}