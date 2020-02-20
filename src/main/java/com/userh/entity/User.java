package com.userh.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.userh.enums.ProfileEnum;

public class User implements Serializable {
	
	private static final long serialVersionUID = -3397969694482305183L;

	private Integer id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Set<Integer> profile = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Integer> getProfile() {
		return profile;
	}

	public void setProfile(Set<Integer> profile) {
		this.profile = profile;
	}
    
	public Set<ProfileEnum> getProfileEnums(){
		return profile.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
	}
    

}
