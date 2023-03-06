package com.javaguide.springboot.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javaguide.springboot.repository.UserRepository;
import com.javaguide.springboot.seviceImpl.UserServiceImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		if(userPhone.equals(userService.getUserByPhn(userPhone).getPhone())){
//			return new User(userPhone, userService.getUserByPhn(userPhone).getPassword(), new ArrayList<>(null));
//			
//		}
		if(userRepository.findByphone(userPhone).isPresent()){
			return new User(userPhone, userRepository.findByphone(userPhone).get().getPassword(), new ArrayList<>());
			
		}
		else
		{
			throw new UsernameNotFoundException("user Not Found");
		}
		
	}

}
