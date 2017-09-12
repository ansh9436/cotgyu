package net.cot_pr1.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.cot_pr1.dao.UserDao;

@Service
public class User_sService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(User_sService.class);
	@Autowired
	private UserDao userDao;
	
	@Override
	public User_s loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//이부분에  dao 랑 연결해서 네임 등등 가져와야하는 듯 싶다!  >> 비밀번호 오휴 뜸;; 
		logger.info("username : " + username);
		User_s user = new User_s();
		user = userDao.findname(username);
		user.setUsername(user.getUsername());
		user.setPassword(user.getPassword());
		Role role = new Role();
		role.setName("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setAuthorities(roles);
		/*
		
		String password = "aabcb987e4b425751e210413562e78f776de6285";
		User_s user = new User_s();
		user.setUsername(username);
		user.setPassword(password);
		Role role = new Role();
		role.setName("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setAuthorities(roles);
		*/
		
		return user;
	}
}
