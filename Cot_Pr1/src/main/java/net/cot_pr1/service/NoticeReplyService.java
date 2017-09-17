package net.cot_pr1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cot_pr1.dao.NoticeReplyDao;
import net.cot_pr1.domain.NoticeReply;


@Service
public class NoticeReplyService {
	
	@Autowired
	NoticeReplyDao noticereplyDao;
	public void create(NoticeReply vo) {
		noticereplyDao.create(vo);
	}

	public List<NoticeReply> list(int bnum) {
		return noticereplyDao.list(bnum);
	}

	public void delete(int rnum) {
		noticereplyDao.delete(rnum);
	}

	public void update(NoticeReply vo) throws Exception {
		noticereplyDao.update(vo);
	}

	public NoticeReply detail(Integer rnum) {
		
		return noticereplyDao.detail(rnum);
	}

	public void stepshape(NoticeReply vo) {
		noticereplyDao.stepshape(vo);
	}

	public void createcomment(NoticeReply vo) {
		noticereplyDao.createcomment(vo);
	}

	public void create_setgroup(NoticeReply vo) {
		noticereplyDao.create_setgroup(vo);
	}

}
