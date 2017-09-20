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
		//��ȣȭ�� �ٽ� ã�Ƽ� �Ұ�!
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
		//�̺κп�  dao �� �����ؼ� ���� ��� �����;��ϴ� �� �ʹ�!  >> ��й�ȣ ���� ��;; >>��ȣȭ Ǯ� �Է��ؼ� �ϴ� ���� 	
	
		User user = new User();
		user = userDao.findname(username);
		//���̵� ������....
		if(user ==null){
			return user;
		}
		
		user.setUserId(user.getUsername());
		user.setPassword(user.getPassword());
		Role role = new Role();
		
		if(username == "������"){
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
	
		if (user == null) throw new UsernameNotFoundException("������ ������ ã�� �� �����ϴ�.");

		
		return user;
	}
//�̸��Ϸ� ���̵�ã�� 
	public String finduserId(String user_email) {
		return userDao.finduserId(user_email);
	}

	public String findusereamil(String user_email) {
		return userDao.finduseremail(user_email);
	}
	//����
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
