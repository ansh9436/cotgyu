package net.cot_pr1.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import net.cot_pr1.domain.WebBoard;
import net.cot_pr1.service.UserService;
import net.cot_pr1.service.WebBoardService;


@Controller
@RequestMapping("/webboard")
public class WebBoardController {
	
	
	@Autowired
	WebBoardService webboardService;
	@Autowired
	UserService userService;
	//�Խ��� ����Ʈ
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
		
		//���ڵ��� ����
		int count = webboardService.countboard(searchOption, keyword);
	
		//������
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		
		List<WebBoard> list = webboardService.Viewlist(start, end, searchOption, keyword);
		 
		List<WebBoard> poplist = webboardService.popboard();
		
		
		
		//�����͸� �ʿ� ����
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); //list
		map.put("count", count); //���ڵ� ����
		map.put("searchOption", searchOption); //�˻� �ɼ�
		map.put("keyword", keyword); //�˻� Ű����
		map.put("boardPage", boardPage); 
		map.put("poplist", poplist);
		
		//�𵨰� ��
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // �ʿ� ����� �����͸� mav�� ����
        mav.setViewName("webboard/webboard"); // �並 list.jsp�� ����
       
        return mav; // list.jsp�� List�� ���޵ȴ�.
	}
	
	
	// �Խù� �ۼ�ȭ�� �̵�
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "webboard/write"; // write.jsp�� �̵�
    }
	
	// �Խù� �ۼ�
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute WebBoard vo, HttpSession session) throws Exception{
	    // session�� ����� userId�� writer�� ����
	    String writer = (String) session.getAttribute("userId");
	    // vo�� writer�� ����
	    vo.setWriter(writer);
	    webboardService.create(vo);
	    return "redirect:list";
	}

	// �Խù� ����
	@RequestMapping(value="view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int bnum, HttpSession session) throws Exception{
        // ��ȸ�� ���� ó��
		webboardService.uphit(bnum);
		
		String userId = webboardService.findByWriter(bnum);		
		String userimg = userService.findByprofile(userId);	
        // ��(������)+��(ȭ��)�� �Բ� �����ϴ� ��ü
        ModelAndView mav = new ModelAndView();
        // ���� �̸�
        mav.setViewName("webboard/view");
        // �信 ������ ������
        mav.addObject("profileimg",userimg);
        mav.addObject("dto", webboardService.read(bnum));
        return mav;
    }
	
	//�� ����â���� ����     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav) throws Exception{
        WebBoard vo = webboardService.detail(bnum);
        // ���̸� ����
        mav.setViewName("webboard/modify");
        // �信 ������ ������ ����
        mav.addObject("vo", vo);
        // replyDetail.jsp�� ������
        return mav;
    }
  
    
    // �Խñ� ����
    // ������ �Է��� ������� @ModelAttribute vo�� ���޵�
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute WebBoard vo) throws Exception{
    	webboardService.update(vo);
        return "redirect:list";
    }
   
    // �Խñ� ����
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
    	webboardService.delete(bnum);
        return "redirect:list";
    }
	
    @RequestMapping(value="/photoUpload")
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
                //���ε� �� ���ϸ�
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