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
import net.cot_pr1.domain.FreeBoard;
import net.cot_pr1.service.FreeBoardService;
import net.cot_pr1.service.UserService;


@Controller
@RequestMapping("/freeboard")
public class FreeBoardController {
	
	@Autowired
	private FreeBoardService freeboardService;
	@Autowired
	private UserService userService;
	
	//�Խ��� ����Ʈ
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
		
		//���ڵ��� ����
		int count = freeboardService.countboard(searchOption, keyword);
		
		//������
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		
		//�Խ��� ����Ʈ, �α�Խ��� ����Ʈ 
		List<FreeBoard> list = freeboardService.Viewlist(start, end, searchOption, keyword);
		
		List<FreeBoard> poplist = freeboardService.popboard();
		
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
        mav.setViewName("freeboard/freeboard"); // �並 list.jsp�� ����
       
        return mav; // list.jsp��  map�� ���޵ȴ�.
	}
	
	// �Խù� �ۼ�ȭ�� �̵�
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "freeboard/write"; // write.jsp�� �̵�
    }
	
	// �Խù� �ۼ�
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute FreeBoard vo, HttpSession session) throws Exception{
	    //writer�� ���ǿ��� ������ id�� ���
	    String writer = (String) session.getAttribute("userId");
	    
	    // freeboard�� writer�� ����
	    vo.setWriter(writer);
	    
	    //vo������ �Խù� ����
	    freeboardService.create(vo);
	    
	    //�ۼ� �� �Խù� list�� �ٽ� �̵�
	    return "redirect:list";
	}

	// �Խù� ����
	@RequestMapping(value="view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int bnum, HttpSession session) throws Exception{
        // ��ȸ�� ���� ó��
		freeboardService.uphit(bnum);
		//bnum�� �޾ƿ� �ۼ��� ã��
		String userId = freeboardService.findByWriter(bnum);
		//�ۼ��ڷ� �̹��� �ҷ�����
		String userimg = userService.findprofile(userId);	
		
        // ��(������)+��(ȭ��)�� �Բ� �����ϴ� ��ü
        ModelAndView mav = new ModelAndView();
        // ���� �̸�
        mav.setViewName("freeboard/view");
        // �信 ������ ������(bnum���� ���� ��������)
        mav.addObject("dto", freeboardService.read(bnum));
        mav.addObject("profileimg",userimg);
        return mav;
    }
  
	
	//�� ����â���� ����     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav){
    	//bnum���� ���� ������ freeboard vo�� ���� 
        FreeBoard vo = freeboardService.detail(bnum);
        // ���̸� ����
        mav.setViewName("freeboard/modify");
        // �信 ������ ������ ����
        mav.addObject("vo", vo);
        return mav;
    }
    
    // �Խñ� ����
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute FreeBoard vo) throws Exception{
    	//������ ���� ������Ʈ
    	freeboardService.update(vo);
        return "redirect:list";
    }
   
    // �Խñ� ����
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
    	//bnum�� �´� �Խù� ���� ó��
    	freeboardService.delete(bnum);
        return "redirect:list";
    }
	
    //�Խù� �ۼ��� ���� ���ε� �κ�(���̹� ����Ʈ������)
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