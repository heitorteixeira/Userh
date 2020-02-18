package com.userh.enums;


public enum ProfileEnum {
		
	ADMIN(1, "ROLE_ADMIN"),	
	USER(2, "ROLE_USER");
	
	private int code;
	private String description;
	
	private ProfileEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static ProfileEnum toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		for (ProfileEnum x : ProfileEnum.values()) {
			if (code.equals(x.getCode())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Invalid Id: " + code);
	}
}