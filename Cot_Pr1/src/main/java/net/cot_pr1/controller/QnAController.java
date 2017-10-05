package net.cot_pr1.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.cot_pr1.common.BoardPage;
import net.cot_pr1.common.PhotoVo;
import net.cot_pr1.domain.QnA;
import net.cot_pr1.service.QnAService;
import net.cot_pr1.service.UserService;


@Controller
@RequestMapping("/qna")
public class QnAController {
	
	@Autowired
	private QnAService qnaService;
	@Autowired
	private UserService userService;
	
	//�Խ��� ����Ʈ
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
				
		int count = qnaService.countboard(searchOption, keyword);
		
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		
		List<QnA> list = qnaService.Viewlist(start, end, searchOption, keyword);
		
		List<QnA> poplist = qnaService.popboard();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); 
		map.put("count", count); 
		map.put("searchOption", searchOption); 
		map.put("keyword", keyword);
		map.put("boardPage", boardPage); 
		map.put("poplist", poplist);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); 
        mav.setViewName("qna/qna"); 
       
        return mav; 
	}
	
	//�Խù� �ۼ�ȭ�� �̵�
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "qna/write"; 
    }
	
	//�Խù� �ۼ�
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public ModelAndView insert(@ModelAttribute QnA vo, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
	    
		String writer = (String) session.getAttribute("userId");
	    vo.setAnswer("q");
	    vo.setWriter(writer);
	    
	    qnaService.create(vo);
	    
	    int bnum = vo.getBnum();
	    System.out.println(bnum);
	  	qnaService.setgroup(vo);
	    
	   
	    mav.setViewName("redirect:list");
	    return mav;
	}

	//�Խù� ����
	@RequestMapping(value="view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int bnum, HttpSession session) throws Exception{
      
		qnaService.uphit(bnum);
	
		String userId = qnaService.findByWriter(bnum);
		String userimg = userService.findprofile(userId);	
    
        ModelAndView mav = new ModelAndView();
      
        mav.setViewName("qna/view");
        mav.addObject("dto", qnaService.read(bnum));
        mav.addObject("profileimg",userimg);
        return mav;
    }
	
	//�亯�ۼ�ȭ�� �̵�
	@RequestMapping(value="writeanswer/{bnum}", method=RequestMethod.GET)
    public ModelAndView writeanswer(@PathVariable("bnum") int bnum,ModelAndView mav){
		
		mav.setViewName("qna/writeanswer");
	    mav.addObject("bnum", bnum);
		
        return mav; 
    }
		
	//�� �亯
	@RequestMapping(value="answer", method=RequestMethod.POST)
	public String answer(@ModelAttribute QnA vo, HttpSession session, @RequestParam int bnum) throws Exception{
	  
	    String writer = (String) session.getAttribute("userId");
	    vo.setQgroup(bnum);
	    vo.setWriter(writer);
	    vo.setAnswer("a");
	  
	    qnaService.create(vo);
	    return "redirect:list";
	}
 
	//�� ����â���� ����     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav){
        QnA vo = qnaService.detail(bnum);
     
        mav.setViewName("qna/modify");       
        mav.addObject("vo", vo);     
        return mav;
    }
    
    //�Խñ� ����
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute QnA vo) throws Exception{
    	qnaService.update(vo);
        return "redirect:list";
    }
   
    // �Խñ� ����
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
    	qnaService.delete(bnum);
        return "redirect:list";
    }
	
    //�Խù� ���� ���ε�
    @RequestMapping("/photoUpload")
    public String photoUpload(HttpServletRequest request, PhotoVo vo){
        String callback = vo.getCallback();
        String callback_func = vo.getCallback_func();
        String file_result = "";
        try {
            if(vo.getFiledata() != null && vo.getFiledata().getOriginalFilename() != null && !vo.getFiledata().getOriginalFilename().equals("")){
                //������ �����ϸ�
                String original_name = vo.getFiledata().getOriginalFilename();
                String ext = original_name.substring(original_name.lastIndexOf(".")+1);
                //���� �⺻���
                String defaultPath = request.getSession().getServletContext().getRealPath("/");
                //���� �⺻��� _ �󼼰��
                String path = defaultPath + "resources" + File.separator + "Editors" + File.separator +"uploadimg" + File.separator;             
                File file = new File(path);
                System.out.println("path:"+path);
                //���丮 �������� ������� ���丮 ����
                if(!file.exists()) {
                    file.mkdirs();
                }
                //������ ���ε� �� ���ϸ�(�ѱ۹����� ���� ���������� �ø��� �ʴ°��� ����)
                String realname = UUID.randomUUID().toString() + "." + ext;
            ///////////////// ������ ���Ͼ��� /////////////////
                vo.getFiledata().transferTo(new File(path+realname));
                file_result += "&bNewLine=true&sFileName="+original_name+"&sFileURL=/resources/Editors/uploadimg/"+realname;
            } else {
                file_result += "&errstr=error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + callback + "?callback_func="+callback_func+file_result;
    }

 
	
}