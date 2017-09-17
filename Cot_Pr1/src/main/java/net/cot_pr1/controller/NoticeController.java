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
import net.cot_pr1.domain.Notice;
import net.cot_pr1.service.NoticeService;
import net.cot_pr1.service.UserService;


@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserService userService;
	
	//�Խ��� ����Ʈ
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
		
		//���ڵ��� ����
		int count = noticeService.countboard(searchOption, keyword);
		
		//������
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		
		List<Notice> list = noticeService.Viewlist(start, end, searchOption, keyword);
		
		//�����͸� �ʿ� ����
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); //list
		map.put("count", count); //���ڵ� ����
		map.put("searchOption", searchOption); //�˻� �ɼ�
		map.put("keyword", keyword); //�˻� Ű����
		map.put("boardPage", boardPage); 
		
		//�𵨰� ��
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // �ʿ� ����� �����͸� mav�� ����
        mav.setViewName("notice/notice"); // �並 list.jsp�� ����
       
        return mav; // list.jsp�� List�� ���޵ȴ�.
	}
	
	//02 �Խù� �ۼ�ȭ�� �̵�
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "notice/write"; // write.jsp�� �̵�
    }
	
	//03 �Խù� �ۼ�
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute Notice vo, HttpSession session) throws Exception{
	    // session�� ����� userId�� writer�� ����
	    String writer = (String) session.getAttribute("userId");
	    // vo�� writer�� ����
	    vo.setWriter(writer);
	    noticeService.create(vo);
	    return "redirect:list";
	}

	//04 �Խù� ����
	@RequestMapping(value="view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int bnum, HttpSession session) throws Exception{
        // ��ȸ�� ���� ó��
		noticeService.uphit(bnum);
	
		
		String userId = noticeService.findByWriter(bnum);
		String userimg = userService.findByprofile(userId);	
        // ��(������)+��(ȭ��)�� �Բ� �����ϴ� ��ü
        ModelAndView mav = new ModelAndView();
        // ���� �̸�
        mav.setViewName("notice/view");
        // �信 ������ ������
        mav.addObject("dto", noticeService.read(bnum));
        mav.addObject("profileimg",userimg);
        return mav;
    }
  
	
	//�� ����â���� ����     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav){
        Notice vo = noticeService.detail(bnum);
        // ���̸� ����
        mav.setViewName("notice/modify");
        // �信 ������ ������ ����
        mav.addObject("vo", vo);
        // replyDetail.jsp�� ������
        return mav;
    }
    // 05. �Խñ� ����
    // ������ �Է��� ������� @ModelAttribute BoardVO vo�� ���޵�
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute Notice vo) throws Exception{
    	noticeService.update(vo);
        return "redirect:list";
    }
   
    // 06. �Խñ� ����
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
    	noticeService.delete(bnum);
        return "redirect:list";
    }
	
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