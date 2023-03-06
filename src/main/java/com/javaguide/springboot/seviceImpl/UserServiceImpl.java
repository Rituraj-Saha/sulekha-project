package com.javaguide.springboot.seviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.javaguide.springboot.exception.ResourceNotFoundException;
import com.javaguide.springboot.helper.JwtUtil;
import com.javaguide.springboot.model.CreateUserResponse;
import com.javaguide.springboot.model.Seesions;
import com.javaguide.springboot.model.User;
import com.javaguide.springboot.repository.SessionRepository;
import com.javaguide.springboot.repository.UserRepository;
import com.javaguide.springboot.services.CustomUserDetailsService;
import com.javaguide.springboot.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private SessionRepository sessionRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public UserServiceImpl(UserRepository userRepository,SessionRepository sessionRepo) {
		super();
		this.userRepository = userRepository;
		this.sessionRepo = sessionRepo;
	}

	@Override
	public User getUserByPhn(String phn) {
		// TODO Auto-generated method stub
		if(userRepository.findByphone(phn).isPresent())
		{
			return userRepository.findByphone(phn).get();
		}
		else 
			{
				throw new ResourceNotFoundException("user","phone",phn);
			}
	}

	@Override
	public CreateUserResponse createUser(User user) throws Exception{
		// TODO Auto-generated method stub
		
		try {
			
			User newUser = userRepository.save(user);
			
			try {
				this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getPhone(), user.getPassword()));
			
			}
			catch(BadCredentialsException e) {
				e.printStackTrace();
				throw new Exception("Bad credentials");
				//return ResponseEntity.ok(new JwtResponse("User Not Found"));
			}
			//fine area
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(user.getPhone());
			String token = this.jwtUtil.generateToken(userDetails);
			
			sessionRepo.save(new Seesions(user.getPhone(),token,"Logged In"));
			
			return new CreateUserResponse(newUser, token);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e);
			
		}
		
	}

}
