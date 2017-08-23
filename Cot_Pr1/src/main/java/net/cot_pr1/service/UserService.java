package net.cot_pr1.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cot_pr1.dao.UserDao;
import net.cot_pr1.domain.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void create(User user) {
		userDao.create(user);
	}

	public User findByID(String userId) {
		return userDao.findByID(userId);
	}

	public void imgmodify(User vo) {
		userDao.imgmodify(vo);
		
	}

	public void update(User user) {
		userDao.update(user);
		
	}

	public int checkId(User vo) {
		return userDao.checkId(vo);
	}

}
