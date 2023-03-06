package com.javaguide.springboot.model;

import lombok.Data;

@Data
public class JwtRequest {

	String phone;
	String password;
}
