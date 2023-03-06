package com.javaguide.springboot.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javaguide.springboot.exception.ResourceNotFoundException;
import com.javaguide.springboot.helper.JwtUtil;
import com.javaguide.springboot.repository.SessionRepository;
import com.javaguide.springboot.services.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private SessionRepository sessionRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//getjwt
		//Bearer
		//validate
		String requestTokenHeader = request.getHeader("Authorization");
		String phone = null;
		String jwtToken = null;
		//null and bearer check
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			if(sessionRepo.findById(jwtToken).get().getStatus().equals("Logged In"))
			{
			try {
					 phone = this.jwtUtil.extractUsername(jwtToken);
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new ResourceNotFoundException("JwtAuthenticationconfigFilter", jwtToken,"No user found" );
				
			}
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(phone);
			if(phone!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			}
			else {
				System.out.println("Error in phone or securitycontext check");
			}
			}
			else {		
				throw new ResourceNotFoundException("JwtAuthenticationconfigFilter", jwtToken, sessionRepo.findById(jwtToken).get().getStatus());
			}
		}
//		else {
//			throw new ResourceNotFoundException("JwtAuthenticationconfigFilter", "Token not found", "");
//		}
		filterChain.doFilter(request, response);
	}
	
}
