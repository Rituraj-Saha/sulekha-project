package com.javaguide.springboot.model;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class CreateUserResponse {

	@Autowired
	User user;
	
	String token;
	public CreateUserResponse(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	
	
}
