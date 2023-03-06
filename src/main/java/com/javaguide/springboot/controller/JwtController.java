package com.javaguide.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javaguide.springboot.helper.JwtUtil;
import com.javaguide.springboot.model.JwtRequest;
import com.javaguide.springboot.model.JwtResponse;
import com.javaguide.springboot.repository.SessionRepository;
import com.javaguide.springboot.services.CustomUserDetailsService;

@RestController
@CrossOrigin(origins = "*") // this is to allow cross origin
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private SessionRepository sessionRepo;
	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		System.out.println(jwtRequest.getPhone()+" pass:"+jwtRequest.getPassword());
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getPhone(), jwtRequest.getPassword()));
		
		}
		catch(BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Bad credentials");
			//return ResponseEntity.ok(new JwtResponse("User Not Found"));
		}
		//fine area
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getPhone());
		String token = this.jwtUtil.generateToken(userDetails);
		System.out.println("jwt: "+token);
		sessionRepo.setSeassonInfoById(token,jwtRequest.getPhone(),"Logged In");
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
