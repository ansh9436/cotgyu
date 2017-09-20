package net.cot_pr1.service;




import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.cot_pr1.dao.UserDao;
import net.cot_pr1.domain.Message;
import net.cot_pr1.domain.User;
import net.cot_pr1.security.Role;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	@Autowired 
	private PasswordEncoder passwordEncoder;

	public void create(User user) {
		//암호화는 다시 찾아서 할것!
		String password = user.getPassword();
		password = passwordEncoder.encode(password);
		user.setPassword(password);
		
		userDao.create(user);
	}

	public User findByID(String userId) {
		return userDao.findByID(userId);
	}

	public void imgmodify(User vo) {
		userDao.imgmodify(vo);
		
	}

	public void update(User user) {
		String password = user.getPassword();
		password = passwordEncoder.encode(password);
		user.setPassword(password);
		
		userDao.update(user);
		
	}

	public int checkId(User vo) {
		return userDao.checkId(vo);
	}

	public String findByprofile(String userId) {	
		return userDao.findByprofile(userId);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {	
		//이부분에  dao 랑 연결해서 네임 등등 가져와야하는 듯 싶다!  >> 비밀번호 오휴 뜸;; >>복호화 풀어서 입력해서 일단 성공 	
	
		User user = new User();
		user = userDao.findname(username);
		//아이디가 없을때....
		if(user ==null){
			return user;
		}
		
		user.setUserId(user.getUsername());
		user.setPassword(user.getPassword());
		Role role = new Role();
		
		if(username == "관리자"){
			role.setName("ROLE_ADMIN");
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			user.setAuthorities(roles);
			return user;

		}
		role.setName("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setAuthorities(roles);
	
		if (user == null) throw new UsernameNotFoundException("접속자 정보를 찾을 수 없습니다.");

		
		return user;
	}
//이메일로 아이디찾기 
	public String finduserId(String user_email) {
		return userDao.finduserId(user_email);
	}

	public String findusereamil(String user_email) {
		return userDao.finduseremail(user_email);
	}
	//쪽지
	public void sendmessage(Message message) {
		userDao.sendmessage(message);
	}

	public List<Message> viewmessage(String userid) {
		return userDao.viewmessage(userid);
		
	}

	public void unregister(String userid) {
		userDao.unregister(userid);
	}
	
	
}
