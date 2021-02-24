package com.github.jeff1Six.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.github.jeff1Six.security.UserSS;

public class UserService {
	
	
	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		} catch (Exception e) {
			return null ;
		}
	}
	
}
