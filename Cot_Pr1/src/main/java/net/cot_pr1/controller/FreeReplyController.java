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

import net.cot_pr1.domain.FreeReply;
import net.cot_pr1.service.FreeReplyService;


@RestController
@RequestMapping("/freereply")
public class FreeReplyController {
    
    @Autowired
    FreeReplyService freereplyService;
    
    // ��� �Է�
    @RequestMapping(value="insert.do", method=RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute FreeReply vo, HttpSession session,@RequestParam int bnum, @RequestParam String replytext){
    	
    	ModelAndView mav = new ModelAndView();
    	
        String userId = (String) session.getAttribute("userId");
    
        
        vo.setReplyer(userId);
        freereplyService.create(vo);
        int rnum = vo.getRnum();  
        vo.setRegroup(rnum);
        freereplyService.create_setgroup(vo);
        
        bnum = vo.getBnum();
        mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/freeboard/view?bnum={bnum}");
        
        return mav;
  
    }
      
    // ��� ���(@Controller��� : veiw(ȭ��)�� ����)
    @RequestMapping("list.do")
    public ModelAndView list(@RequestParam int bnum, ModelAndView mav){
        List<FreeReply> list = freereplyService.list(bnum);
        // ���̸� ����
        mav.setViewName("freeboard/replylist");	
        // �信 ������ ������ ����
        mav.addObject("bnum", bnum);
        mav.addObject("list", list);
        // replyList.jsp�� ������
        return mav;
    }
      
    // ��� ���(@RestController Json������� ó�� : �����͸� ����)
    @RequestMapping("listJson.do")
    @ResponseBody // ���ϵ����͸� json���� ��ȯ(��������)
    public List<FreeReply> listJson(@RequestParam int bnum){
        List<FreeReply> list = freereplyService.list(bnum);
        return list;
    }
    
    @RequestMapping("delete")
    public ModelAndView replydelete(@RequestParam int rnum, @RequestParam int bnum) throws Exception{
    	freereplyService.delete(rnum);
    	
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/freeboard/view?bnum={bnum}");
    	
    	return mav;
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    public ModelAndView replyupdate(@ModelAttribute FreeReply vo,  @RequestParam int bnum, @RequestParam int rnum, @RequestParam String replytext) throws Exception{
    	ModelAndView mav = new ModelAndView();
    	
    	freereplyService.update(vo);
    	
    	mav.addObject("bnum", bnum);
    	mav.setViewName("redirect:/freeboard/view?bnum={bnum}");
    		
    	return mav; 	
    }
//��� ����â���� ����     
    @RequestMapping(value="/detail/{rnum}", method=RequestMethod.GET)
    public ModelAndView replyDetail(@PathVariable("rnum") Integer rnum, ModelAndView mav){
        FreeReply vo = freereplyService.detail(rnum);
        // ���̸� ����
        mav.setViewName("freeboard/replymodify");
        // �信 ������ ������ ����
        mav.addObject("vo", vo);
        // replyDetail.jsp�� ������
        return mav;
    }
  
  //��� ����â���� ����     
    @RequestMapping(value="/commentwrite/{rnum}", method=RequestMethod.GET)
    public ModelAndView commentwrite(@PathVariable("rnum") Integer rnum, ModelAndView mav){
        FreeReply vo = freereplyService.detail(rnum);
        // ���̸� ����
        mav.setViewName("freeboard/replycomment");
        // �信 ������ ������ ����
        mav.addObject("vo", vo);
        // replyDetail.jsp�� ������
        return mav;
    }    
    
    @RequestMapping(value="comment")
    public ModelAndView replycomment(@ModelAttribute FreeReply vo, HttpSession session, @RequestParam int bnum, 
    		@RequestParam String replytext, @RequestParam int regroup, @RequestParam int restep, @RequestParam int reindent){
    	ModelAndView mav = new ModelAndView();
    	vo.setRegroup(regroup);
    	vo.setRestep(restep);
    	vo.setReindent(reindent);
    	freereplyService.stepshape(vo); 
    	
    	String userId = (String)session.getAttribute("userId");
    	vo.setReplyer(userId);
    	
    	freereplyService.createcomment(vo); //�ڸ�Ʈ����
    	//�����ϰ� ������ ������
    	bnum = vo.getBnum();        
        mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/freeboard/view?bnum={bnum}");
        
    	return mav;
    }
}