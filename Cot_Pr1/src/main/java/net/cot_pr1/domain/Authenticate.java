package net.cot_pr1.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Authenticate {
	@NotEmpty @Size(min=4, max=12)
	private String userid;
	@NotEmpty @Size(min=4, max=12)
	private String password;
	
	public Authenticate() {
		
	}
	
	public Authenticate(String userId, String password) {
		super();
		this.userid = userId;
		this.password = password;
	}

	public String getUserId() {
		return userid;
	}


	public void setUserId(String userId) {
		this.userid = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public boolean matchPassword(String password) {
		return password.equals(password);
	}
	
	
	
	
}
