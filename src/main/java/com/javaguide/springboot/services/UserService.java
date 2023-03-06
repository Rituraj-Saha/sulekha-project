package com.javaguide.springboot.services;

import com.javaguide.springboot.model.CreateUserResponse;
import com.javaguide.springboot.model.User;

public interface UserService{
	User getUserByPhn(String phone);
	CreateUserResponse createUser(User user) throws Exception;
}
