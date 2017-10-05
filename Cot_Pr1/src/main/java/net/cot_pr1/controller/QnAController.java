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
	
	//게시판 리스트
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
	
	//게시물 작성화면 이동
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "qna/write"; 
    }
	
	//게시물 작성
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

	//게시물 보기
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
	
	//답변작성화면 이동
	@RequestMapping(value="writeanswer/{bnum}", method=RequestMethod.GET)
    public ModelAndView writeanswer(@PathVariable("bnum") int bnum,ModelAndView mav){
		
		mav.setViewName("qna/writeanswer");
	    mav.addObject("bnum", bnum);
		
        return mav; 
    }
		
	//글 답변
	@RequestMapping(value="answer", method=RequestMethod.POST)
	public String answer(@ModelAttribute QnA vo, HttpSession session, @RequestParam int bnum) throws Exception{
	  
	    String writer = (String) session.getAttribute("userId");
	    vo.setQgroup(bnum);
	    vo.setWriter(writer);
	    vo.setAnswer("a");
	  
	    qnaService.create(vo);
	    return "redirect:list";
	}
 
	//글 수정창으로 연결     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav){
        QnA vo = qnaService.detail(bnum);
     
        mav.setViewName("qna/modify");       
        mav.addObject("vo", vo);     
        return mav;
    }
    
    //게시글 수정
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute QnA vo) throws Exception{
    	qnaService.update(vo);
        return "redirect:list";
    }
   
    // 게시글 삭제
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
    	qnaService.delete(bnum);
        return "redirect:list";
    }
	
    //게시물 사진 업로드
    @RequestMapping("/photoUpload")
    public String photoUpload(HttpServletRequest request, PhotoVo vo){
        String callback = vo.getCallback();
        String callback_func = vo.getCallback_func();
        String file_result = "";
        try {
            if(vo.getFiledata() != null && vo.getFiledata().getOriginalFilename() != null && !vo.getFiledata().getOriginalFilename().equals("")){
                //파일이 존재하면
                String original_name = vo.getFiledata().getOriginalFilename();
                String ext = original_name.substring(original_name.lastIndexOf(".")+1);
                //파일 기본경로
                String defaultPath = request.getSession().getServletContext().getRealPath("/");
                //파일 기본경로 _ 상세경로
                String path = defaultPath + "resources" + File.separator + "Editors" + File.separator +"uploadimg" + File.separator;             
                File file = new File(path);
                System.out.println("path:"+path);
                //디렉토리 존재하지 않을경우 디렉토리 생성
                if(!file.exists()) {
                    file.mkdirs();
                }
                //서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
                String realname = UUID.randomUUID().toString() + "." + ext;
            ///////////////// 서버에 파일쓰기 /////////////////
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