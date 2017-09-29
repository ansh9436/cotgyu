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
	
	//게시판 리스트
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
		
		//레코드의 개수
		int count = freeboardService.countboard(searchOption, keyword);
		
		//페이지
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		
		//게시판 리스트, 인기게시판 리스트 
		List<FreeBoard> list = freeboardService.Viewlist(start, end, searchOption, keyword);
		
		List<FreeBoard> poplist = freeboardService.popboard();
		
		//데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); //list
		map.put("count", count); //레코드 개수
		map.put("searchOption", searchOption); //검색 옵션
		map.put("keyword", keyword); //검색 키워드
		map.put("boardPage", boardPage); 
		map.put("poplist", poplist);
		//모델과 뷰
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
        mav.setViewName("freeboard/freeboard"); // 뷰를 list.jsp로 설정
       
        return mav; // list.jsp로  map이 전달된다.
	}
	
	// 게시물 작성화면 이동
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "freeboard/write"; // write.jsp로 이동
    }
	
	// 게시물 작성
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute FreeBoard vo, HttpSession session) throws Exception{
	    //writer에 세션에서 가져온 id값 사용
	    String writer = (String) session.getAttribute("userId");
	    
	    // freeboard에 writer를 세팅
	    vo.setWriter(writer);
	    
	    //vo정보로 게시물 생성
	    freeboardService.create(vo);
	    
	    //작성 후 게시물 list로 다시 이동
	    return "redirect:list";
	}

	// 게시물 보기
	@RequestMapping(value="view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int bnum, HttpSession session) throws Exception{
        // 조회수 증가 처리
		freeboardService.uphit(bnum);
		//bnum을 받아와 작성자 찾기
		String userId = freeboardService.findByWriter(bnum);
		//작성자로 이미지 불러오기
		String userimg = userService.findprofile(userId);	
		
        // 모델(데이터)+뷰(화면)를 함께 전달하는 객체
        ModelAndView mav = new ModelAndView();
        // 뷰의 이름
        mav.setViewName("freeboard/view");
        // 뷰에 전달할 데이터(bnum으로 정보 가져오기)
        mav.addObject("dto", freeboardService.read(bnum));
        mav.addObject("profileimg",userimg);
        return mav;
    }
  
	
	//글 수정창으로 연결     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav){
    	//bnum으로 정보 가져와 freeboard vo에 저장 
        FreeBoard vo = freeboardService.detail(bnum);
        // 뷰이름 지정
        mav.setViewName("freeboard/modify");
        // 뷰에 전달할 데이터 지정
        mav.addObject("vo", vo);
        return mav;
    }
    
    // 게시글 수정
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute FreeBoard vo) throws Exception{
    	//수정한 내용 업데이트
    	freeboardService.update(vo);
        return "redirect:list";
    }
   
    // 게시글 삭제
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
    	//bnum에 맞는 게시물 삭제 처리
    	freeboardService.delete(bnum);
        return "redirect:list";
    }
	
    //게시물 작성시 사진 업로드 부분(네이버 스마트에디터)
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