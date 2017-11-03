package com.yann.dto;


import com.yann.entity.User;

public class UserDto extends User {
    
    private Integer pId;

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
}