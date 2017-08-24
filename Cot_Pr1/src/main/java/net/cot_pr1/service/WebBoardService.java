package net.cot_pr1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cot_pr1.dao.UserDao;
import net.cot_pr1.dao.WebBoardDao;
import net.cot_pr1.domain.WebBoard;

@Service
public class WebBoardService {

	@Autowired
	WebBoardDao webboardDao;
	@Autowired
	UserDao userDao;
	
	
	
	public int countboard(String searchOption, String keyword) throws Exception {
		return webboardDao.countboard(searchOption, keyword);
	}

	public List<WebBoard> Viewlist(int start, int end, String searchOption, String keyword) throws Exception {
		return webboardDao.Viewlist(start, end, searchOption, keyword);
	}

	public List<WebBoard> popboard() throws Exception {
		return webboardDao.popboard();
	}

	public void create(WebBoard vo) throws Exception {
		webboardDao.create(vo);
		
	}

	public void uphit(int bnum) throws Exception{
		webboardDao.uphit(bnum);
		
	}

	public String findByWriter(int bnum) throws Exception {
		return webboardDao.findByWriter(bnum);
	}

	public String findByprofile(String userId) throws Exception{
		return userDao.findByprofile(userId);
	}

	public Object read(int bnum) throws Exception{
		return webboardDao.read(bnum);
	}

	public WebBoard detail(Integer bnum) throws Exception{
		return webboardDao.detail(bnum);
	}

	public void update(WebBoard vo) throws Exception{
		webboardDao.update(vo);	
	}

	public void delete(int bnum) throws Exception{
		webboardDao.delete(bnum);
	}

}
