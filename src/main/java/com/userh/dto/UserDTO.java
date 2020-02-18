package com.userh.dto;

import com.userh.entities.User;

public class UserDTO {

	private User user;
	
	public UserDTO() {
		this.user = new User();
	}

	public Integer getId() {
		return user.getId();
	}
	
	public void setId(Integer id) {
		user.setId(id);
	}
	
	public String getName() {
		return user.getName();
	}
	
	public void setName(String name) {
		user.setName(name);
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public void setEmail(String email) {
		user.setEmail(email);
	}
	
	public String getPassword() {
		return user.getPassword();
	}
	
	public void setPassword(String passord) {
		user.setPassword(passord);
	}
	
	public String getAddress() {
		return user.getAddress();
	}
	
	public void setAddress(String address) {
		user.setAddress(address);
	}
	
	public String getPhone() {
		return user.getPhone();
	}
	
	public void setPhone(String phone) {
		user.setPhone(phone);
	}
}
