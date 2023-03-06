package com.javaguide.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaguide.springboot.helper.JwtUtil;
import com.javaguide.springboot.model.CreateUserResponse;
import com.javaguide.springboot.model.JwtRequest;
import com.javaguide.springboot.model.User;
import com.javaguide.springboot.services.UserService;


@RestController
public class Home {

		@Autowired
		JwtUtil jwtUtil;
		
		@Autowired
		UserService userService;
		
		@RequestMapping(value = "/welcome", method = RequestMethod.GET)
		public String welocome()
		{
			String text = "This is private page";
			return text;
		}
		
		@RequestMapping(value = "/get-user", method = RequestMethod.GET)
		public User getUser(@RequestHeader ("Authorization") String token)
		{
			String phone = this.jwtUtil.extractUsername(token.substring(7));
			return userService.getUserByPhn(phone);
		}
		
		@RequestMapping(value="/token/create-user", method = RequestMethod.POST)
		ResponseEntity<?> createUser(@RequestBody User user) throws Exception{
			CreateUserResponse nUser = userService.createUser(user);
			return ResponseEntity.ok(nUser);
		}
		//for login
		//use Token
		
}


